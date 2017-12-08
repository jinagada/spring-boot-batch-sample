package com.example.mapper;

import com.example.annotation.MasterDb;
import com.example.model.SampleOrgModel;

import java.util.List;

@MasterDb
public interface  SampleOrgMapper {
    public List<SampleOrgModel> selectSampleOrg() throws Exception;
}
