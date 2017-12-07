package com.example.mapper;

import com.example.annotation.MasterDb;
import com.example.model.SampleOrg;

import java.util.List;

@MasterDb
public interface  SampleOrgMapper {
    public List<SampleOrg> selectSampleOrg() throws Exception;
}
