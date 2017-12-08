package com.example.mapper;

import com.example.annotation.SlaveDb;
import com.example.model.SampleTagModel;

import java.util.List;

@SlaveDb
public interface SampleTagMapper {
    public List<SampleTagModel> selectSampleTag() throws Exception;

    public int insertSampleTag(SampleTagModel param) throws Exception;
}
