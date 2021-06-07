package com.example.togepi.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class BatchScheduler {

    @Autowired
    private SimpleJobLauncher jobLauncher;

    @Autowired
    private Job job;

//    @Scheduled(fixedRate = 100000, initialDelay = 2000)
    public void executeUserJob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        final long start = System.currentTimeMillis();
        log.info("Job started at {}", LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

        final String jobId = String.valueOf(System.currentTimeMillis());
        final JobParameters param = new JobParametersBuilder().addString("JobID", jobId).toJobParameters();
        final JobExecution execution = jobLauncher.run(job, param);

        final long end = System.currentTimeMillis();
        final String duration = new DecimalFormat("#.##").format((end - start) / 1000.0f);
        log.info("Job finished with status {} and duration of {} seconds", execution.getStatus(), duration);
    }
}