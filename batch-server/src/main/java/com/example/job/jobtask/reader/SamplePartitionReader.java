package com.example.job.jobtask.reader;

import com.example.mapper.SampleOrgMapper;
import com.example.model.SampleOrgModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@StepScope
@Slf4j
public class SamplePartitionReader implements ItemReader<SampleOrgModel> {
    @Autowired
    private SampleOrgMapper sampleOrgMapper;
    @Value("#{stepExecutionContext[from]}")
    private String from;
    @Value("#{stepExecutionContext[offset]}")
    private String offset;

    private List<SampleOrgModel> items;

    @Override
    public SampleOrgModel read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        SampleOrgModel item;
        if (items == null) {
            log.debug("SamplePartitionReader start");
            final Map<String, Object> param = new HashMap<>();
            param.put("from", from);
            param.put("offset", offset);
            log.debug("SamplePartitionReader from : {}, offset : {}", from, offset);
            final List<SampleOrgModel> list = sampleOrgMapper.selectSampleOrg(param);
            final Optional<List<SampleOrgModel>> sampleOrgs = Optional.ofNullable(list);
            log.debug("SamplePartitionReader items size : {}", sampleOrgs.orElse(new ArrayList<>()).size());
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
