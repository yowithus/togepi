package com.example.togepi.sample;

import com.example.togepi.enums.ShapeType;

public class Circle extends AbstractShape {

    private double radius;

    public Circle(double radius) {
        this.name = ShapeType.CIRCLE.getValue();
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * Math.pow(this.radius, 2);
    }
}
