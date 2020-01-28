package com.example.togepi.controller;

import com.example.togepi.aspect.Hello;
import com.example.togepi.dto.HelloRequestDto;
import com.example.togepi.dto.HelloResponseDto;
import com.example.togepi.service.MainService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class MainController {

    @Autowired
    @Qualifier("defaultMainService")
    private MainService defaultMainService;

    @Autowired
    @Qualifier("secondMainService")
    private MainService secondMainService;

    @Autowired
    private ObjectMapper objectMapper;

    @Hello
    @GetMapping(value = "/hello")
    public ResponseEntity<String> hello() throws Exception {
        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Test-Header", "TestHeader");

        final String responseBody = defaultMainService.hello();
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(responseBody);
    }

    @GetMapping(value = "/hello2")
    public String secondHello() {
        return secondMainService.hello();
    }

    @PostMapping(value = "/hello3")
    public HelloResponseDto thirdHello(@Valid @RequestBody HelloRequestDto helloRequestDto) {
        return secondMainService.postHello(helloRequestDto);
    }
}
