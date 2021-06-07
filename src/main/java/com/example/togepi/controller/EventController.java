package com.example.togepi.controller;

import com.example.togepi.dto.EventRequestDto;
import com.example.togepi.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {
	
	@Autowired
	@Qualifier("rabbitMQService")
	private EventService rabbitMQService;
	
	@Autowired
	@Qualifier("kafkaService")
	private EventService kafkaService;
	
	@Value("${kafka.topic}")
	private String kafkaTopic;
	
	@Value("${rabbitmq.routing.key.1}")
	private String rabbitMQRoutingKey;
	
	@PostMapping(value = "/event/{eventSoftware}")
	public void sendMessage(@PathVariable String eventSoftware, @RequestBody EventRequestDto eventRequestDto) {
		switch(eventSoftware) {
			case "kafka":
				kafkaService.send(kafkaTopic, eventRequestDto.getMessage());
				break;
			case "rabbitmq":
			default:
				rabbitMQService.send(rabbitMQRoutingKey, eventRequestDto.getMessage());
				break;
		}
	}
}
