package com.example.togepi.sample;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractShape implements Shape {

    protected String name;

    @Override
    public void draw() {
        log.info("Drawing " + name);
    }
}
