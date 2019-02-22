package com.example.togepi.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

public class CSVExpenseJobListener extends JobExecutionListenerSupport {

    private static final Logger logger = LoggerFactory.getLogger(CSVExpenseJobListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.info("Before job execution");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        logger.info("After job execution");
    }
}