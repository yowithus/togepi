package com.example.togepi.controller;

import com.example.togepi.service.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitMQController {

    @Autowired
    private RabbitMQService rabbitMQService;

    @PostMapping(value = "/rabbitmq")
    public ResponseEntity<Void> publish() {
        rabbitMQService.publish();
        return ResponseEntity.ok().build();
    }
}
