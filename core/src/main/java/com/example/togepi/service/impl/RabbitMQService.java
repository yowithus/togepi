package com.example.togepi.service.impl;

import com.example.togepi.config.RabbitMQConfig;
import com.example.togepi.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("rabbitMQService")
@Slf4j
public class RabbitMQService implements EventService {

    @Autowired
    private RabbitMQConfig rabbitMQConfig;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void send(String key, String message) {
        final String exchange = rabbitMQConfig.getExchange();
        rabbitTemplate.convertAndSend(exchange, key, message);
        log.info("Sending message: " + message);
    }

    @Override
    public void receive(String message) {
        log.info("Receiving message: " + message);
    }
}
