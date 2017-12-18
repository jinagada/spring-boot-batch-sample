package com.example.controller;

import com.example.model.JobExecutionModel;
import com.example.model.JobInstanceModel;
import com.example.model.JobScheduleModel;
import com.example.model.StepExecutionModel;
import com.example.service.JobDetailService;
import com.example.service.JobScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@Slf4j
public class JobManageController {
    @Autowired
    private JobScheduleService jobScheduleService;
    @Autowired
    private JobDetailService jobDetailService;

    @GetMapping(value = {"/", "/main"})
    public String main() {
        return "html/main";
    }

    @GetMapping(value = "/about")
    public String about() {
        return "html/about";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "html/login";
    }

    @GetMapping(value = "/batch/web/scheduleList")
    public String scheduleList(@ModelAttribute(value = "jobModel") JobScheduleModel jobScheduleModel) {
        try {
            jobScheduleModel.setJobScheduleModelList(jobScheduleService.getJobScheduleList());
        } catch (Exception e) {
            log.error("scheduleList ERROR", e);
        }
        return "html/batch/scheduleList";
    }

    @GetMapping(value = "/batch/web/jobDetail")
    public String jobDetail(@ModelAttribute(value = "jobDetail") JobInstanceModel jobInstanceModel) {
        return "html/batch/jobDetail";
    }

    @GetMapping(value = "/batch/web/jobInstanceList.ajax")
    public String jobInstanceList(@ModelAttribute(value = "jobIns") JobInstanceModel jobInstanceModel) {
        try {
            jobInstanceModel.setJobInstanceList(jobDetailService.getJobInstanceList(jobInstanceModel));
        } catch (Exception e) {
            log.error("jobInstanceList ERROR", e);
        }
        return "html/batch/jobInstanceList";
    }

    @GetMapping(value = "/batch/web/jobExecutionList.ajax")
    public String jobExecutionList(@ModelAttribute(value = "jobExec") JobExecutionModel jobExecutionModel) {
        try {
            jobExecutionModel.setJobExecutionList(jobDetailService.getJobExecutionListByJobInstanceId(jobExecutionModel));
        } catch (Exception e) {
            log.error("jobExecutionList ERROR", e);
        }
        return "html/batch/jobExecutionList";
    }

    @GetMapping(value = "/batch/web/stepExecutionList.ajax")
    public String stepExecutionList(@ModelAttribute(value = "stepExec") StepExecutionModel stepExecutionModel) {
        try {
            stepExecutionModel.setStepExecutionList(jobDetailService.getStepExecutionList(stepExecutionModel));
        } catch (Exception e) {
            log.error("stepExecutionList ERROR", e);
        }
        return "html/batch/stepExecutionList";
    }

    @GetMapping(value = "/batch/web/executionJobList")
    public String executionJobList(@ModelAttribute(value = "execJobs") JobExecutionModel jobExecutionModel) {
        try {
            if (jobExecutionModel.getPageNo() < 1) {
                jobExecutionModel.setPageNo(1);
            }
            jobExecutionModel.setPageRows(10);
            jobExecutionModel.setJobExecutionList(jobDetailService.selectExecutionJobsList(jobExecutionModel));
        } catch (Exception e) {
            log.error("executionJobList ERROR", e);
        }
        return "html/batch/executionJobList";
    }
}
