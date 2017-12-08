package com.example.job.jobtask.writer;

import com.example.mapper.SampleTagMapper;
import com.example.model.SampleTag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Slf4j
public class SampleWriter implements ItemWriter<SampleTag> {
    @Autowired
    private SampleTagMapper sampleTagMapper;

    @Override
    public void write(List<? extends SampleTag> items) throws Exception {
        final Optional<List<? extends SampleTag>> writeList = Optional.ofNullable(items);
        if (writeList.isPresent()) {
            writeList.get().stream().forEach(entity -> {
                try {
                    sampleTagMapper.insertSampleTag(entity);
                } catch (Exception e) {
                    log.error("SampleWriter ERROR", e);
                }
            });
        }
    }
}
