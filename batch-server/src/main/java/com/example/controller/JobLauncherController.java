package com.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class JobLauncherController {
    @Autowired
    private JobOperator simpleJobOperator;

    @RequestMapping("/launchJob")
    public long launchJob(@RequestParam(name = "jobName") final String jobName) throws Exception {
        String jobParam = "now=" + System.currentTimeMillis();
        return simpleJobOperator.start(jobName, jobParam);
    }
}
