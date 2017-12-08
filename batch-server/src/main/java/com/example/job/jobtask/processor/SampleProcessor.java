package com.example.job.jobtask.processor;

import com.example.model.SampleOrg;
import com.example.model.SampleTag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

@Slf4j
public class SampleProcessor implements ItemProcessor<SampleOrg, SampleTag> {
    @Override
    public SampleTag process(SampleOrg item) throws Exception {
        SampleTag result = new SampleTag();
        log.debug("SampleProcessor input : {}", item);
        result.setId(item.getId());
        result.setName(item.getName());
        return result;
    }
}
