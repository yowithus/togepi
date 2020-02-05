package com.example.togepi.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

@Slf4j
public class OrderCsvJobListener extends JobExecutionListenerSupport {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Before job execution");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("After job execution");
    }
}