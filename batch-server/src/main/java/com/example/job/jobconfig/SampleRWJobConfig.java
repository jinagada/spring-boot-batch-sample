package com.example.job.jobconfig;

import com.example.job.jobtask.processor.SampleProcessor;
import com.example.job.jobtask.reader.SampleReader;
import com.example.job.jobtask.writer.SampleWriter;
import com.example.job.listener.JobCompletionNotificationListener;
import com.example.model.SampleOrgModel;
import com.example.model.SampleTagModel;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleRWJobConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobCompletionNotificationListener notificationListener;

    @Bean
    public Job sampleRWJob() {
        return jobBuilderFactory.get("sampleRWJob")
                .incrementer(new RunIdIncrementer())
                .listener(notificationListener)
                .start(sampleRWStep())
                .build();
    }

    @Bean
    public Step sampleRWStep() {
        return stepBuilderFactory.get("sampleRWStep")
                .<SampleOrgModel, SampleTagModel>chunk(1)
                .reader(sampleReader())
                .processor(sampleProcessor())
                .writer(sampleWriter())
                .build();
    }

    @Bean
    @StepScope
    public SampleReader sampleReader() {
        return new SampleReader();
    }

    @Bean
    public SampleProcessor sampleProcessor() {
        return new SampleProcessor();
    }

    @Bean
    public SampleWriter sampleWriter() {
        return new SampleWriter();
    }
}
