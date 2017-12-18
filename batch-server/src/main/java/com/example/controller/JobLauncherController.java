package com.example.controller;

import com.example.model.JobExecutionModel;
import com.example.model.JobRunModel;
import com.example.model.JobScheduleModel;
import com.example.service.JobDetailService;
import com.example.service.JobScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.*;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class JobLauncherController {
    @Autowired
    private JobScheduleService jobScheduleService;
    @Autowired
    private JobDetailService jobDetailService;

    @RequestMapping(value = "/batch/schedule/getJobScheduleList", method = RequestMethod.GET)
    public JobScheduleModel getJobScheduleList(@ModelAttribute JobScheduleModel jobScheduleModel) throws NoSuchJobInstanceException, NoSuchJobException {
        jobScheduleModel.setJobScheduleModelList(jobScheduleService.getJobScheduleList());
        return jobScheduleModel;
    }

    @RequestMapping(value = "/batch/schedule/startJob", method = RequestMethod.POST)
    public Long startJob(@ModelAttribute JobRunModel jobRunModel) throws JobParametersInvalidException, JobInstanceAlreadyExistsException, NoSuchJobException {
        return jobScheduleService.startJob(jobRunModel);
    }

    @RequestMapping(value = "/batch/schedule/startSchedule", method = RequestMethod.POST)
    public int startSchedule(@ModelAttribute JobScheduleModel jobScheduleModel) throws NoSuchJobInstanceException, NoSuchJobException {
        return jobScheduleService.startSchedule(jobScheduleModel);
    }

    @RequestMapping(value = "/batch/schedule/stopSchedule", method = RequestMethod.POST)
    public int stopSchedule(@ModelAttribute JobScheduleModel jobScheduleModel) throws NoSuchJobInstanceException, NoSuchJobException {
        return jobScheduleService.stopSchedule(jobScheduleModel);
    }

    @RequestMapping(value = "/batch/schedule/saveSchedule", method = RequestMethod.POST)
    public int saveSchedule(@ModelAttribute JobScheduleModel jobScheduleModel) throws NoSuchJobInstanceException, NoSuchJobException {
        return jobScheduleService.saveSchedule(jobScheduleModel);
    }

    @RequestMapping(value = "/batch/schedule/stopJobExecution", method = RequestMethod.POST)
    public boolean stopJobExecution(@ModelAttribute JobExecutionModel jobExecutionModel) throws NoSuchJobExecutionException, JobExecutionNotRunningException {
        return jobDetailService.stopJobExecution(jobExecutionModel);
    }

    @RequestMapping(value = "/batch/schedule/abandonJobExecution", method = RequestMethod.POST)
    public boolean abandonJobExecution(@ModelAttribute JobExecutionModel jobExecutionModel) throws NoSuchJobExecutionException, JobExecutionAlreadyRunningException {
        boolean isCompleted;
        JobExecution jobExecution = jobDetailService.abandonJobExecution(jobExecutionModel);
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            isCompleted = true;
        } else {
            isCompleted = false;
        }
        return isCompleted;
    }
}
