package com.example.job.jobtask.tasklet;

import com.example.job.code.CommonCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@StepScope
@Slf4j
public class DummyTasklet implements Tasklet {
    @Value("#{jobParameters[dummy]}")
    private String dummy;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.debug("DummyTasklet Execute dummy : {}", dummy);
        final boolean isDummy = StringUtils.equalsIgnoreCase(dummy, CommonCodeEnum.DUMMY.getCode());
        if (isDummy) {
            contribution.setExitStatus(new ExitStatus(CommonCodeEnum.DUMMY.getCode()));
        }
        return RepeatStatus.FINISHED;
    }
}
