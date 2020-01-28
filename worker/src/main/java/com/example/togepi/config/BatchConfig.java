package com.example.togepi.config;

import com.example.togepi.batch.OrderCsvItemProcessor;
import com.example.togepi.batch.OrderCsvItemReader;
import com.example.togepi.batch.OrderCsvItemWriter;
import com.example.togepi.batch.OrderCsvJobListener;
import com.example.togepi.dto.csv.OrderCsvDto;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public ResourcelessTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Bean
    public JobRepository jobRepository(ResourcelessTransactionManager transactionManager) throws Exception {
        final MapJobRepositoryFactoryBean mapJobRepositoryFactoryBean = new MapJobRepositoryFactoryBean(transactionManager);
        mapJobRepositoryFactoryBean.setTransactionManager(transactionManager);
        return mapJobRepositoryFactoryBean.getObject();
    }

    @Bean
    public SimpleJobLauncher jobLauncher(JobRepository jobRepository) {
        final SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
        simpleJobLauncher.setJobRepository(jobRepository);
        return simpleJobLauncher;
    }

    @Bean
    public ItemReader<OrderCsvDto> reader() {
        return new OrderCsvItemReader();
    }

    @Bean
    public ItemProcessor<OrderCsvDto, OrderCsvDto> processor() {
        return new OrderCsvItemProcessor();
    }

    @Bean
    public ItemWriter<OrderCsvDto> writer() {
        return new OrderCsvItemWriter();
    }

    @Bean
    public JobExecutionListener listener() {
        return new OrderCsvJobListener();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(15);
        executor.initialize();
        return executor;
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("expenseJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .flow(step())
                .end()
                .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory.get("expenseStep")
                .<OrderCsvDto, OrderCsvDto>chunk(20)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .taskExecutor(taskExecutor())
                .build();
    }
}
