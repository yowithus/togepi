package com.example.togepi.service.impl;

import org.springframework.stereotype.Service;

@Service("defaultMainService")
public class DefaultMainService extends AbstractMainService {

    @Override
    public String hello() {
        return "hello default";
    }
}
