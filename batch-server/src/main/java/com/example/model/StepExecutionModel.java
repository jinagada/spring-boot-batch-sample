package com.example.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;

import java.util.Date;
import java.util.List;

@Data
@ToString
public class StepExecutionModel {
    private String stepName;
    private BatchStatus status;
    private int readCount;
    private int writeCount;
    private int commitCount;
    private int rollbackCount;
    private int readSkipCount;
    private int processSkipCount;
    private int writeSkipCount;
    private Date startTime;
    private Date endTime;
    private Date lastUpdated;
    private ExitStatus exitStatus;
    private int filterCount;

    private Long jobExecutionId;
    private List<StepExecutionModel> stepExecutionList;

    public StepExecutionModel() {}

    public StepExecutionModel(StepExecution stepExecution) {
        this.stepName = stepExecution.getStepName();
        this.status = stepExecution.getStatus();
        this.readCount = stepExecution.getReadCount();
        this.writeCount = stepExecution.getWriteCount();
        this.commitCount = stepExecution.getCommitCount();
        this.rollbackCount = stepExecution.getRollbackCount();
        this.readSkipCount = stepExecution.getReadSkipCount();
        this.processSkipCount = stepExecution.getProcessSkipCount();
        this.writeSkipCount = stepExecution.getWriteSkipCount();
        this.startTime = stepExecution.getStartTime();
        this.endTime = stepExecution.getEndTime();
        this.lastUpdated = stepExecution.getLastUpdated();
        this.exitStatus = stepExecution.getExitStatus();
        this.filterCount = stepExecution.getFilterCount();
    }
}
