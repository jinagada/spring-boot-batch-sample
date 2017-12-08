package com.example.controller;

import com.example.model.JobRunModel;
import com.example.model.JobScheduleModel;
import com.example.service.JobScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobInstanceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class JobLauncherController {
    @Autowired
    private JobScheduleService jobScheduleService;

    @RequestMapping("/batch/schedule/getJobScheduleList")
    public JobScheduleModel getJobScheduleList(@ModelAttribute JobScheduleModel jobScheduleModel) throws NoSuchJobInstanceException,
            NoSuchJobException {
        jobScheduleModel.setJobScheduleModelList(jobScheduleService.getJobScheduleList());
        return jobScheduleModel;
    }

    @RequestMapping(value = "/batch/schedule/startJob")
    public Long startJob(@ModelAttribute JobRunModel jobRunModel) throws JobParametersInvalidException, JobInstanceAlreadyExistsException, NoSuchJobException {
        return jobScheduleService.startJob(jobRunModel);
    }

    @RequestMapping(value = "/batch/schedule/startSchedule")
    public int startSchedule(@ModelAttribute JobScheduleModel jobScheduleModel) throws NoSuchJobInstanceException, NoSuchJobException {
        return jobScheduleService.startSchedule(jobScheduleModel);
    }

    @RequestMapping(value = "/batch/schedule/stopSchedule")
    public int stopSchedule(@ModelAttribute JobScheduleModel jobScheduleModel) throws NoSuchJobInstanceException, NoSuchJobException {
        return jobScheduleService.stopSchedule(jobScheduleModel);
    }

    @RequestMapping(value = "/batch/schedule/saveSchedule")
    public int saveSchedule(@ModelAttribute JobScheduleModel jobScheduleModel) throws NoSuchJobInstanceException, NoSuchJobException {
        return jobScheduleService.saveSchedule(jobScheduleModel);
    }
}
