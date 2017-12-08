package com.example.mapper;

import com.example.annotation.BatchDb;
import com.example.model.JobScheduleModel;

@BatchDb
public interface JobScheduleBatchMapper {
    public JobScheduleModel selectJobSchedule(String jobName);

    public int updateScheduled(JobScheduleModel param);

    public int insertJobSchedule(JobScheduleModel param);

    public int updateJobSchedule(JobScheduleModel param);
}
