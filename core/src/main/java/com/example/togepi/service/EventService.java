package com.example.togepi.service;

public interface EventService {

    void send(String key, String message);

    void receive(String message);
}
