package com.example.togepi.service.impl;

import org.springframework.stereotype.Service;

@Service("secondMainService")
public class SecondMainService extends AbstractMainService {

    @Override
    public String hello() {
        return "hello second";
    }
}
