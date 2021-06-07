package com.example.togepi.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MainScheduler {
	
//	@Scheduled(fixedRate = 1000)
	public void executeRabbitMQSender() {
		log.info("a");
	}
}
