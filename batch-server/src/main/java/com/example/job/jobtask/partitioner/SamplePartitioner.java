package com.example.job.jobtask.partitioner;

import com.example.mapper.SampleOrgMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SamplePartitioner implements Partitioner {
    @Autowired
    private SampleOrgMapper sampleOrgMapper;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        int from = 0;
        int offset = 0;
        Map<String, ExecutionContext> result = new HashMap<>();
        try {
            ExecutionContext value;
            if (1 < gridSize) {
                final int totalCount = sampleOrgMapper.getSampleOrgTotalCount();
                if (0 < totalCount) {
                    // offset 구하기
                    if (totalCount % gridSize == 0) {
                        offset = totalCount / gridSize;
                    } else {
                        offset = new Double(Math.floor(totalCount / gridSize)).intValue() + 1;
                    }
                    for (int i = 0; i < gridSize; i++) {
                        if (totalCount <= gridSize) {
                            if (i == 0) {
                                value = new ExecutionContext();
                                value.put("from", null);
                                value.put("offset", null);
                                result.put("partition" + i, value);
                            } else {
                                value = new ExecutionContext();
                                value.put("from", "0");
                                value.put("offset", "0");
                                result.put("partition" + i, value);
                            }
                        } else {
                            from = from + offset;
                            value = new ExecutionContext();
                            value.put("from", String.valueOf(from));
                            value.put("offset", String.valueOf(offset));
                            result.put("partition" + i, value);
                        }
                    }
                } else {
                    for (int i = 0; i < gridSize; i++) {
                        value = new ExecutionContext();
                        value.put("from", "0");
                        value.put("offset", "0");
                        result.put("partition" + i, value);
                    }
                }
            } else {
                value = new ExecutionContext();
                value.put("from", null);
                value.put("offset", null);
                result.put("partition0", value);
            }
        } catch (Exception e) {
            log.error("SamplePartitioner ERROR", e);
        }
        return result;
    }
}
