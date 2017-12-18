package com.example.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;

import java.util.Date;
import java.util.List;

@Data
@ToString
public class JobExecutionModel extends CommonModel {
    private Long jobExecutionId;
    private String jobName;
    private Date createTime;
    private Date startTime;
    private Date endTime;
    private BatchStatus status;
    private ExitStatus exitStatus;
    private String exitCode;
    private Date lastUpdated;
    private JobParameters jobParameters;
    private boolean isListAll;
    private int refreshTime;

    private Long jobInstanceId;
    private List<JobExecutionModel> jobExecutionList;
    private List<String> jobNameList;

    public JobExecutionModel () {}

    public JobExecutionModel(Long jobExecutionId, JobExecution jobExecution) {
        this.jobExecutionId = jobExecutionId;
        if (jobExecution != null) {
            this.createTime = jobExecution.getCreateTime();
            this.startTime = jobExecution.getStartTime();
            this.endTime = jobExecution.getEndTime();
            this.status = jobExecution.getStatus();
            this.exitStatus = jobExecution.getExitStatus();
            this.lastUpdated = jobExecution.getLastUpdated();
            this.jobParameters = jobExecution.getJobParameters();
        }
    }
}
