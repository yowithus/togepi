package com.example.togepi.service;

import com.example.togepi.dto.HelloRequestDto;
import com.example.togepi.dto.HelloResponseDto;

public interface MainService {

    String hello();

    HelloResponseDto postHello(HelloRequestDto helloRequestDto);
}
