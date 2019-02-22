package com.example.togepi.scheduler;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private SimpleJobLauncher jobLauncher;

    @Autowired
    private Job job;

//    @Scheduled(fixedRate = 5000)
//    public void reportCurrentTime() {
//        logger.info("The time is now {}", dateFormat.format(new Date()));
//    }

//    @Scheduled(fixedRate = 100000, initialDelay = 2000)
    public void executeExpenseJob() throws Exception {
        final long start = System.currentTimeMillis();
        logger.info("Job started at {}", dateFormat.format(new Date()));

        final String jobId = String.valueOf(System.currentTimeMillis());
        final JobParameters param = new JobParametersBuilder().addString("JobID", jobId).toJobParameters();
        final JobExecution execution = jobLauncher.run(job, param);

        final long end = System.currentTimeMillis();
        final String duration = new DecimalFormat("#.##").format((end - start) / 1000.0f);
        logger.info("Job finished with status {} and duration of {} seconds", execution.getStatus(), duration);
    }
}