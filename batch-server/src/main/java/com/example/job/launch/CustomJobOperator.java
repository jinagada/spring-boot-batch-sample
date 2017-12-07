package com.example.job.launch;

import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.transaction.annotation.Transactional;

public class CustomJobOperator extends SimpleJobOperator {
    @Override
    @Transactional("transactionManager")
    public boolean stop(long executionId) throws NoSuchJobExecutionException, JobExecutionNotRunningException {
        return super.stop(executionId);
    }
}
