package com.example.job.listener;

import com.example.service.AlramService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Slf4j
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
    @Autowired
    private JobOperator jobOperator;
    @Autowired
    private AlramService alramService;
    @Value("${batcherror.beforeerror.receive.email}")
    private String beforeErrorReceiveEmail;
    @Value("${batcherror.beforeerror.title}")
    private String beforeErrorTitle;
    @Value("${batcherror.aftererror.receive.email}")
    private String afterErrorReceiveEmail;
    @Value("${batcherror.aftererror.title}")
    private String afterErrorTitle;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        Set<Long> runningExecution;

        try {
            final String jobName = jobExecution.getJobInstance().getJobName();
            final long instanceId = jobExecution.getJobInstance().getInstanceId();
            runningExecution = jobOperator.getRunningExecutions(jobName);

            // 동시에 실행중인 Job 확인
            if (!runningExecution.isEmpty() && 2 <= runningExecution.size()) {
                // 새로 실행된 Job 중지
                jobExecution.stop();
                log.error("{}-{} Job has been stoped!! because this job is already running!!!", jobName, instanceId);
                final String alramContents = String.format("%s-%d Job has been stoped!! because this job is already running!!!", jobName,
                        instanceId);
                // 메일 발송
                alramService.alramSend(beforeErrorReceiveEmail, beforeErrorTitle, alramContents, "mail.vm");
            }

            log.debug("beforeJob : {}-{}", jobName, instanceId);

            super.beforeJob(jobExecution);
        } catch (Exception e) {
            log.error("{}", e);
        }
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        try {
            final String jobName = jobExecution.getJobInstance().getJobName();
            final long instanceId = jobExecution.getJobInstance().getInstanceId();
            final String exitCode = jobExecution.getExitStatus().getExitCode();

            // 정상종료, 강제중지 여부 확인
            if (jobExecution.getStatus() != BatchStatus.COMPLETED && jobExecution.getStatus() != BatchStatus.STOPPED) {
                log.error("{}-{} Job has been error!!! Exit Code is {}", jobName, instanceId, exitCode);
                final String alramContents = String.format("%s-%d Job has been error!!! Exit Code is %s", jobName,
                        instanceId, exitCode);
                // 메일 발송
                alramService.alramSend(afterErrorReceiveEmail, afterErrorTitle, alramContents, "mail.vm");
            }

            log.debug("afterJob : {}-{}", jobName, instanceId);

            super.afterJob(jobExecution);
        } catch (Exception e) {
            log.error("{}", e);
        }
    }
}
