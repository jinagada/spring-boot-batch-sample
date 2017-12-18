package com.example.job.jobtask.processor;

import com.example.model.SampleOrgModel;
import com.example.model.SampleTagModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SampleProcessor implements ItemProcessor<SampleOrgModel, SampleTagModel> {
    @Override
    public SampleTagModel process(SampleOrgModel item) throws Exception {
        final SampleTagModel result = new SampleTagModel();
        log.debug("SampleProcessor input : {}", item);
        result.setId(item.getId());
        result.setName(item.getName());
        Thread.sleep(10000);// 10 seconds
        return result;
    }
}
