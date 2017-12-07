package com.example.config;

import com.example.job.launch.CustomJobOperator;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@ComponentScan(basePackages = {"com.example.job"})
public class BatchConfig implements BatchConfigurer {
    @Autowired
    private DataSource batchDataSource;

    @Override
    public JobRepository getJobRepository() throws Exception {
        JobRepositoryFactoryBean factoryBean = new JobRepositoryFactoryBean();
        factoryBean.setDataSource(batchDataSource);
        factoryBean.setTransactionManager(getTransactionManager());
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    @Override
    public PlatformTransactionManager getTransactionManager() {
        return new DataSourceTransactionManager(batchDataSource);
    }

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
        JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
        jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
        return jobRegistryBeanPostProcessor;
    }

    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

    @Override
    public JobLauncher getJobLauncher() throws Exception {
        SimpleJobLauncher launcher = new SimpleJobLauncher();
        launcher.setJobRepository(getJobRepository());
        launcher.setTaskExecutor(taskExecutor());
        launcher.afterPropertiesSet();
        return launcher;
    }

    @Override
    public JobExplorer getJobExplorer() throws Exception {
        JobExplorerFactoryBean explorerFactoryBean = new JobExplorerFactoryBean();
        explorerFactoryBean.setDataSource(batchDataSource);
        explorerFactoryBean.afterPropertiesSet();
        return explorerFactoryBean.getObject();
    }

    @Bean(name = "simpleJobOperator")
    public JobOperator getJobOperator(JobRegistry jobRegistry, JobExplorer jobExplorer) throws Exception {
        SimpleJobOperator jobOperator = new CustomJobOperator();
        jobOperator.setJobRegistry(jobRegistry);
        jobOperator.setJobRepository(getJobRepository());
        jobOperator.setJobExplorer(jobExplorer);
        jobOperator.setJobLauncher(getJobLauncher());
        return jobOperator;
    }
}
