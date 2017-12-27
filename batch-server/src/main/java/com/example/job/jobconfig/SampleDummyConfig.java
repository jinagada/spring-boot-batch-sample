package com.example.job.jobconfig;

import com.example.job.code.CommonCodeEnum;
import com.example.job.jobtask.tasklet.DummyTasklet;
import com.example.job.jobtask.tasklet.SampleTasklet;
import com.example.job.listener.JobCompletionNotificationListener;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleDummyConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobCompletionNotificationListener notificationListener;
    @Autowired
    private SampleTasklet sampleTasklet;
    @Autowired
    private DummyTasklet dummyTasklet;

    @Bean
    public Job sampleDummyJob() {
        return jobBuilderFactory.get("sampleDummyJob")
                .incrementer(new RunIdIncrementer())
                .listener(notificationListener)
                .flow(sampleDummyStep()).on(CommonCodeEnum.DUMMY.getCode()).end()
                .from(sampleDummyStep()).on(ExitStatus.COMPLETED.getExitCode()).to(sampleTaskletStep1())
                .next(sampleTaskletStep2()).on("*").end()
                .end()
                .build();
    }

    @Bean
    public Step sampleDummyStep() {
        return stepBuilderFactory.get("sampleDummyStep")
                .tasklet(dummyTasklet)
                .build();
    }

    @Bean
    public Step sampleTaskletStep1() {
        return stepBuilderFactory.get("sampleTaskletStep1")
                .tasklet(sampleTasklet)
                .build();
    }

    @Bean
    public Step sampleTaskletStep2() {
        return stepBuilderFactory.get("sampleTaskletStep2")
                .tasklet(sampleTasklet)
                .build();
    }
}
