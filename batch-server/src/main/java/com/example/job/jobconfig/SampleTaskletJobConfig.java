package com.example.job.jobconfig;

import com.example.job.jobtask.tasklet.SampleTasklet;
import com.example.job.listener.JobCompletionNotificationListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleTaskletJobConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobCompletionNotificationListener notificationListener;
    @Autowired
    private SampleTasklet sampleTasklet;

    @Bean
    public Job sampleTaskletJob() {
        return jobBuilderFactory.get("sampleTaskletJob")
                .incrementer(new RunIdIncrementer())
                .listener(notificationListener)
                .start(sampleTaskletStep())
                .build();
    }

    @Bean
    public Step sampleTaskletStep() {
        return stepBuilderFactory.get("sampleTaskletStep")
                .tasklet(sampleTasklet)
                .build();
    }
}
