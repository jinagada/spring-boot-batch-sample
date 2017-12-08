package com.example.job.jobtask.reader;

import com.example.mapper.SampleOrgMapper;
import com.example.model.SampleOrg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class SampleReader implements ItemReader<SampleOrg> {
    @Autowired
    private SampleOrgMapper sampleOrgMapper;

    private List<SampleOrg> items;

    @Override
    public SampleOrg read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        SampleOrg item;
        if (items == null) {
            log.debug("SampleReader start");
            final List<SampleOrg> list = sampleOrgMapper.selectSampleOrg();
            final Optional<List<SampleOrg>> sampleOrgs = Optional.ofNullable(list);
            log.debug("SampleReader items size : {}", sampleOrgs.orElse(new ArrayList<>()).size());
            items = sampleOrgs.orElse(new ArrayList<>());
        }
        if (!items.isEmpty()) {
            item = items.remove(0);
        } else {
            item = null;
        }
        return item;
    }
}
