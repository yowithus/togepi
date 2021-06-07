package com.example.togepi.listener;

import com.example.togepi.service.EventService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class EventListener {

	@Autowired
	@Qualifier("kafkaService")
	private EventService kafkaService;

	@Autowired
	@Qualifier("rabbitMQService")
	private EventService rabbitMQService;

//	@KafkaListener(topics = "${kafka.topic}")
	public void receiveKafkaMessage(String message) {
		kafkaService.receive(message);
	}
	
//	@RabbitListener(queues = "${rabbitmq.queue.1}")
	public void receiveRabbitMQMessage1(String message) {
		rabbitMQService.receive(message);
	}
	
//	@RabbitListener(queues = "${rabbitmq.queue.2}")
	public void receiveRabbitMQMessage2(String message) {
		rabbitMQService.receive(message);
	}
}
