package com.example.mapper;

import com.example.annotation.MasterDb;
import com.example.model.SampleOrgModel;

import java.util.List;
import java.util.Map;

@MasterDb
public interface  SampleOrgMapper {
    public List<SampleOrgModel> selectSampleOrg(Map<String, Object> param) throws Exception;

    public int getSampleOrgTotalCount() throws Exception;
}
