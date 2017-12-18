package com.example.mapper;

import com.example.annotation.BatchDb;
import com.example.model.JobExecutionModel;
import com.example.model.JobScheduleModel;

import java.util.List;

@BatchDb
public interface JobScheduleBatchMapper {
    public JobScheduleModel selectJobSchedule(String jobName);

    public int updateScheduled(JobScheduleModel param);

    public int insertJobSchedule(JobScheduleModel param);

    public int updateJobSchedule(JobScheduleModel param);

    public int selectExecutionJobsCount(JobExecutionModel param);

    public List<JobExecutionModel> selectExecutionJobsList(JobExecutionModel param);
}
