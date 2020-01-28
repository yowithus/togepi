package com.example.togepi.sample;

public abstract class AbstractShape implements Shape {

    protected String name;

    @Override
    public void draw() {
        System.out.println("Drawing " + name);
    }
}
