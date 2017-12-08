package com.example.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class JobInstanceModel extends CommonModel {
    private Long jobInstanceId;
    private String jobName;
    private JobExecutionModel lastExecution;
    private String summary;
    private int refreshTime;

    private List<JobInstanceModel> jobInstanceList;
}
