package com.example.togepi.service.impl;

import com.example.togepi.config.RabbitMQConfig;
import com.example.togepi.service.RabbitMQService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQServiceImpl implements RabbitMQService {
    private static final Logger LOG = LoggerFactory.getLogger(MainServiceImpl.class.getName());

    @Autowired
    private RabbitMQConfig rabbitMQConfig;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void publish() {
        final String exchange = rabbitMQConfig.getExchange();
        final String routingKey = rabbitMQConfig.getRoutingKey();
        final String message = "Yon";
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
        LOG.info("Sending message: " + message);
    }

    @Override
//    @RabbitListener(queues = "${togepi.queue.name}")
    public void consume(String message) {
        final String queue = rabbitMQConfig.getQueue();
        final String routingKey = rabbitMQConfig.getRoutingKey();
        LOG.info("Receiving message: " + message);
    }
}
