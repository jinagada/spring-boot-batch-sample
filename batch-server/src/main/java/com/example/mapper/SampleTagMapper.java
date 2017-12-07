package com.example.mapper;

import com.example.annotation.SlaveDb;
import com.example.model.SampleTag;

import java.util.List;

@SlaveDb
public interface SampleTagMapper {
    public List<SampleTag> selectSampleTag() throws Exception;

    public int insertSampleTag(SampleTag param) throws Exception;
}
