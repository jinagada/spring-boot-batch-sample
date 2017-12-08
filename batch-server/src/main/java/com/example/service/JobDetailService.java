package com.example.service;

import com.example.model.JobExecutionModel;
import com.example.model.JobInstanceModel;
import com.example.model.StepExecutionModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.*;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class JobDetailService {
    @Autowired
    private JobOperator simpleJobOperator;

    @Autowired
    private JobExplorer jobExplorer;

    public List<JobInstanceModel> getJobInstanceList(JobInstanceModel jobInstanceModel) throws NoSuchJobException, NoSuchJobInstanceException, NoSuchJobExecutionException {
        List<JobInstanceModel> jobInstanceList = new ArrayList<>();
        jobInstanceModel.setTotalCount(jobExplorer.getJobInstanceCount(jobInstanceModel.getJobName()));
        List<Long> jobInstanceIdList = simpleJobOperator.getJobInstances(jobInstanceModel.getJobName(),
                ((jobInstanceModel.getPageNo() - 1) * jobInstanceModel.getPageRows()), jobInstanceModel.getPageRows());
        for (Long jobInstanceId : jobInstanceIdList) {
            JobInstanceModel jobInstance = new JobInstanceModel();
            jobInstance.setJobInstanceId(jobInstanceId);
            jobInstance.setJobName(jobInstanceModel.getJobName());
            jobInstance.setLastExecution(getLastJobExecution(jobInstanceId));
            try {
                jobInstance.setSummary(simpleJobOperator.getSummary(jobInstanceId));
            } catch (Exception e) {
                jobInstance.setSummary("None");
                log.error("{}", e.getMessage());
            }
            jobInstanceList.add(jobInstance);
        }
        return jobInstanceList;
    }

    public JobExecutionModel getLastJobExecution(String jobName) throws NoSuchJobException, NoSuchJobInstanceException {
        List<Long> jobInstanceIdList = simpleJobOperator.getJobInstances(jobName, 0, 1); // get lastest JobInstance id
        if (jobInstanceIdList == null || jobInstanceIdList.isEmpty()) return null;
        List<Long> jobExecutionIdList = simpleJobOperator.getExecutions(jobInstanceIdList.get(0)); // get lastest JobExecutionModel id
        if (jobExecutionIdList == null || jobExecutionIdList.isEmpty()) return null;
        JobExecution jobExecution = jobExplorer.getJobExecution(jobExecutionIdList.get(0));
        return new JobExecutionModel(jobExecutionIdList.get(0), jobExecution);
    }

    public JobExecutionModel getLastJobExecution(Long jobInstanceId) throws NoSuchJobInstanceException {
        List<Long> jobExecutionIdList = simpleJobOperator.getExecutions(jobInstanceId);
        if (jobExecutionIdList == null || jobExecutionIdList.isEmpty()) return null;
        JobExecution jobExecution = jobExplorer.getJobExecution(jobExecutionIdList.get(0));
        return new JobExecutionModel(jobExecutionIdList.get(0), jobExecution);
    }

    public List<JobExecutionModel> getJobExecutionListByJobInstanceId(JobExecutionModel jobExecutionVo) throws NoSuchJobInstanceException {
        List<JobExecutionModel> jobExecutionList = new ArrayList<>();
        List<Long> jobExecutionIdList = simpleJobOperator.getExecutions(jobExecutionVo.getJobInstanceId());
        for (Long jobExecutionId : jobExecutionIdList) {
            JobExecution jobExecution = jobExplorer.getJobExecution(jobExecutionId);
            JobExecutionModel getJobExecutionModel = new JobExecutionModel(jobExecutionId, jobExecution);
            getJobExecutionModel.setListAll(false);
            getJobExecutionModel.setExitCode(getJobExecutionModel.getExitStatus().getExitCode());
            jobExecutionList.add(getJobExecutionModel);
        }
        return jobExecutionList;
    }

    public List<StepExecutionModel> getStepExecutionList(StepExecutionModel stepExecutionVo) {
        List<StepExecutionModel> stepExecutionList = new ArrayList<>();

        try {
            Iterator<StepExecution> stepExecutionIterator = jobExplorer.getJobExecution(stepExecutionVo.getJobExecutionId()).getStepExecutions().iterator();
            while (stepExecutionIterator.hasNext()) {
                StepExecutionModel stepExecution = new StepExecutionModel(stepExecutionIterator.next());
                stepExecutionList.add(stepExecution);
            }
        } catch (Exception e) {
            log.error("{}", e.getMessage());
        }

        return stepExecutionList;
    }

    public boolean stopJobExecution(JobExecutionModel jobExecutionVo) throws NoSuchJobExecutionException, JobExecutionNotRunningException {
        return simpleJobOperator.stop(jobExecutionVo.getJobExecutionId());
    }

    public JobExecution abandonJobExecution(JobExecutionModel jobExecutionVo) throws NoSuchJobExecutionException, JobExecutionAlreadyRunningException {
        return simpleJobOperator.abandon(jobExecutionVo.getJobExecutionId());
    }
}
