package com.example.togepi.controller;

import com.example.togepi.dto.http.ZomatoResponseDto;
import com.example.togepi.service.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
public class HttpController {

    @Autowired
    @Qualifier("asyncHttpService")
    private HttpService asyncHttpService;
    
    @Autowired
    @Qualifier("retrofitHttpService")
    private HttpService retrofitHttpService;
    
    @Autowired
    @Qualifier("httpClientService")
    private HttpService httpClientService;

    @GetMapping(value = "/http/{httpLibrary}")
    public ZomatoResponseDto getRestaurant(@PathVariable String httpLibrary) {
        switch(httpLibrary) {
            case "async":
                return asyncHttpService.getRestaurant();
            case "retrofit":
                return retrofitHttpService.getRestaurant();
            case "httpclient":
            default:
                return httpClientService.getRestaurant();
        }
    }
}
