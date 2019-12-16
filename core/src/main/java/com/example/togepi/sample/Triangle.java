package com.example.togepi.sample;

import com.example.togepi.enums.ShapeType;

public class Triangle extends AbstractShape {

    private double base;
    private double height;

    public Triangle(double base, double height) {
        this.name = ShapeType.TRIANGLE.getValue();
        this.base = base;
        this.height = height;
    }

    @Override
    public double getArea() {
        return this.base * this.height / 2;
    }
}
