package com.example.job.jobconfig;

import com.example.job.jobtask.partitioner.SamplePartitioner;
import com.example.job.jobtask.processor.SampleProcessor;
import com.example.job.jobtask.reader.SamplePartitionReader;
import com.example.job.jobtask.writer.SampleWriter;
import com.example.job.listener.JobCompletionNotificationListener;
import com.example.model.SampleOrgModel;
import com.example.model.SampleTagModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

@Configuration
@Slf4j
public class SamplePartitionRWJobConfig {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobCompletionNotificationListener notificationListener;
    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private SamplePartitioner samplePartitioner;
    @Autowired
    private SamplePartitionReader samplePartitionReader;
    @Autowired
    private SampleProcessor sampleProcessor;
    @Autowired
    private SampleWriter sampleWriter;

    @Bean
    public Job samplePartitionRWJob() {
        return jobBuilderFactory.get("samplePartitionRWJob")
                .incrementer(new RunIdIncrementer())
                .listener(notificationListener)
                .start(samplePartitionStep())
                .build();
    }

    @Bean
    public Step samplePartitionStep() {
        return stepBuilderFactory.get("samplePartitionStep")
                .partitioner(samplePartitionRWStep().getName(), samplePartitioner)
                .partitionHandler(samplePartitionHandler())
                .build();
    }

    @Bean
    public PartitionHandler samplePartitionHandler() {
        TaskExecutorPartitionHandler partitionHandler = new TaskExecutorPartitionHandler();
        partitionHandler.setGridSize(5);
        partitionHandler.setStep(samplePartitionRWStep());
        partitionHandler.setTaskExecutor(taskExecutor);
        try {
            partitionHandler.afterPropertiesSet();
        } catch (Exception e) {
            log.error("SamplePartitionRWJobConfig PartitionHandler ERROR", e);
        }
        return  partitionHandler;
    }

    @Bean
    public Step samplePartitionRWStep() {
        return stepBuilderFactory.get("samplePartitionRWStep")
                .<SampleOrgModel, SampleTagModel>chunk(1)
                .reader(samplePartitionReader)
                .processor(sampleProcessor)
                .writer(sampleWriter)
                .build();
    }
}
