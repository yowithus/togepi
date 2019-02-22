package com.example.togepi.service;

public interface RabbitMQService {

    void publish();

    void consume(String message);
}
