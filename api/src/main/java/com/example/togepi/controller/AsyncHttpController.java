package com.example.togepi.controller;

import com.example.togepi.dto.http.ZomatoResponseDto;
import com.example.togepi.service.AsyncHttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AsyncHttpController {

    @Autowired
    private AsyncHttpService asyncHttpService;

    @GetMapping(value = "/http")
    public ZomatoResponseDto getRestaurant() {
        return asyncHttpService.getRestaurant().join();
    }
}
