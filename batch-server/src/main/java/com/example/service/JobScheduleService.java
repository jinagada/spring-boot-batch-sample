package com.example.service;

import com.example.mapper.JobScheduleBatchMapper;
import com.example.model.JobExecutionModel;
import com.example.model.JobRunModel;
import com.example.model.JobScheduleModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobInstanceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

// Service가 아닌 Component로 해야 정상동작함
@Component
@Slf4j
public class JobScheduleService implements SchedulingConfigurer {
    @Autowired
    private JobOperator simpleJobOperator;

    private ScheduledTaskRegistrar scheduledTaskRegistrar;

    @Autowired
    private JobDetailService jobDetailService;

    @Autowired
    private JobScheduleBatchMapper jobScheduleBatchMapper;

    public Long startJob(JobRunModel jobRunModel) throws JobInstanceAlreadyExistsException, JobParametersInvalidException, NoSuchJobException {
        return startJobWithDateParameter(jobRunModel.getJobName(), jobRunModel.getJobParameters());
    }

    public List<JobScheduleModel> getJobScheduleList() throws NoSuchJobInstanceException, NoSuchJobException {
        List<JobScheduleModel> list = new ArrayList<>();
        Iterator<String> jobNameIterator = simpleJobOperator.getJobNames().iterator();
        while (jobNameIterator.hasNext()) {
            String jobName = jobNameIterator.next();
            JobScheduleModel jobScheduleModel = new JobScheduleModel(jobName);
            JobScheduleModel _jobScheduleModel = jobScheduleBatchMapper.selectJobSchedule(jobName);
            if (_jobScheduleModel != null) {
                jobScheduleModel.setJobScheduleId(_jobScheduleModel.getJobScheduleId());
                jobScheduleModel.setCronExpression(_jobScheduleModel.getCronExpression());
                jobScheduleModel.setScheduled(_jobScheduleModel.getScheduled());
                jobScheduleModel.setDescription(_jobScheduleModel.getDescription());
                jobScheduleModel.setJobParameter(_jobScheduleModel.getJobParameter());
                jobScheduleModel.setRdate(_jobScheduleModel.getRdate());
                jobScheduleModel.setMdate(_jobScheduleModel.getMdate());
            }
            JobExecutionModel jobExecutionModel = jobDetailService.getLastJobExecution(jobName);
            if (jobExecutionModel != null) {
                jobScheduleModel.setLastStatus(jobExecutionModel.getStatus());
                jobScheduleModel.setLastUpdateTime(jobExecutionModel.getLastUpdated());
            }
            list.add(jobScheduleModel);
        }
        return list;
    }

    private void resetJobSchedules() throws NoSuchJobInstanceException, NoSuchJobException {
        log.warn("job schedules reset!!");
        scheduledTaskRegistrar.destroy();

        registerJobSchedules();

        scheduledTaskRegistrar.afterPropertiesSet();
    }

    private void registerJobSchedules() throws NoSuchJobInstanceException, NoSuchJobException {
        Map<Runnable, Trigger> triggerTasks = new HashMap<>();
        for (final JobScheduleModel jobScheduleModel : getJobScheduleList()) {
            if (jobScheduleModel.getCronExpression() != null && !"".equals(jobScheduleModel.getCronExpression())) {
                triggerTasks.put(new Runnable() {
                    @Override
                    public void run() {
                        if (jobScheduleModel.getScheduled() == 1) {
                            try {
                                startJobWithDateParameter(jobScheduleModel.getJobName(), jobScheduleModel.getJobParameter());
                            } catch (NoSuchJobException | JobInstanceAlreadyExistsException | JobParametersInvalidException e) {
                                log.error("{}", e);
                            }
                        }
                    }
                }, new CronTrigger(jobScheduleModel.getCronExpression()));
            }
        }

        scheduledTaskRegistrar.setTriggerTasks(triggerTasks);
    }

    private Long startJobWithDateParameter(String jobName, String jobParameters) throws JobParametersInvalidException, JobInstanceAlreadyExistsException, NoSuchJobException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String _jobParameters = "now=" + sdf.format(new Date());
        if (jobParameters != null && !"".equals(jobParameters)) {
            _jobParameters += "," + jobParameters;
        }
        return simpleJobOperator.start(jobName, _jobParameters);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        this.scheduledTaskRegistrar = taskRegistrar;

        try {
            log.warn("job schedules will be registered!!");
            registerJobSchedules();
        } catch (NoSuchJobInstanceException | NoSuchJobException e) {
            log.error("{}", e);
        }
    }

    public int startSchedule(JobScheduleModel jobScheduleModel) throws NoSuchJobInstanceException, NoSuchJobException {
        jobScheduleModel.setScheduled(1);// 스케줄 시작
        int count = jobScheduleBatchMapper.updateScheduled(jobScheduleModel);
        log.warn("[{}] job schedule will be started!!", jobScheduleModel.getJobName());
        resetJobSchedules();
        return count;
    }

    public int stopSchedule(JobScheduleModel jobScheduleModel) throws NoSuchJobInstanceException, NoSuchJobException {
        jobScheduleModel.setScheduled(0);// 스케줄 중지
        int count = jobScheduleBatchMapper.updateScheduled(jobScheduleModel);
        log.warn("[{}] job schedule will be stoped!!", jobScheduleModel.getJobName());
        resetJobSchedules();
        return count;
    }

    public int saveSchedule(JobScheduleModel jobScheduleModel) throws NoSuchJobInstanceException, NoSuchJobException {
        JobScheduleModel _jobScheduleModel = jobScheduleBatchMapper.selectJobSchedule(jobScheduleModel.getJobName());
        int count = 1;
        if (_jobScheduleModel == null) {
            jobScheduleBatchMapper.insertJobSchedule(jobScheduleModel);
            log.warn("[{}] job schedule is registered!!", jobScheduleModel.getJobName());
        } else {
            jobScheduleBatchMapper.updateJobSchedule(jobScheduleModel);
            log.warn("[{}] job schedule is updated!!", jobScheduleModel.getJobName());
        }
        resetJobSchedules();
        return count;
    }
}
