package com.example.controller;

import com.example.model.JobScheduleModel;
import com.example.service.JobScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.jws.WebParam;
import java.util.List;

@Controller
@Slf4j
public class JobManageController {
    @Autowired
    private JobScheduleService jobScheduleService;

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
    public String scheduleList(Model model) {
        try {
            List<JobScheduleModel> joblist = jobScheduleService.getJobScheduleList();
            model.addAttribute("joblist", joblist);
        } catch (Exception e) {
            log.error("scheduleList ERROR", e);
        }
        return "html/batch/scheduleList";
    }
}
