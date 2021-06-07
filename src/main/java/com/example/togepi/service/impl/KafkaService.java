package com.example.togepi.service.impl;

import com.example.togepi.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service("kafkaService")
@Slf4j
public class KafkaService implements EventService {
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Override
	public void send(String topic, String message) {
		kafkaTemplate.send(topic, message);
		log.info("Sending message: " + message);
	}
	
	@Override
	public void receive(String message) {
		log.info("Receiving message: " + message);
	}
}
