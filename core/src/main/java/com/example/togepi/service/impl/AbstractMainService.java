package com.example.togepi.service.impl;

import com.example.togepi.dto.HelloRequestDto;
import com.example.togepi.dto.HelloResponseDto;
import com.example.togepi.service.MainService;

public abstract class AbstractMainService implements MainService {

    @Override
    public HelloResponseDto postHello(HelloRequestDto helloRequestDto) {
        return null;
    }
}
