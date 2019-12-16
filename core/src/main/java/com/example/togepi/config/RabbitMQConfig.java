package com.example.togepi.config;

import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.queue.1}")
    private String queue1;

    @Value("${rabbitmq.queue.2}")
    private String queue2;

    @Value("${rabbitmq.routing.key.1}")
    private String routingKey1;

    @Value("${rabbitmq.routing.key.2}")
    private String routingKey2;

    @Value("${rabbitmq.routing.key.3}")
    private String routingKey3;

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Queue queue1() {
        return new Queue(queue1);
    }

    @Bean
    public Queue queue2() {
        return new Queue(queue2);
    }

    @Bean
    public Binding binding1a(TopicExchange exchange, Queue queue1) {
        return BindingBuilder.bind(queue1).to(exchange).with(routingKey1);
    }

    @Bean
    public Binding binding1b(TopicExchange exchange, Queue queue1) {
        return BindingBuilder.bind(queue1).to(exchange).with(routingKey2);
    }

    @Bean
    public Binding binding2(TopicExchange exchange, Queue queue2) {
        return BindingBuilder.bind(queue2).to(exchange).with(routingKey3);
    }
}
