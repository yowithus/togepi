package com.example.togepi.sample;

import com.example.togepi.enums.ShapeType;

public class Rectangle extends AbstractShape {

    private double height;
    private double width;

    public Rectangle(double height, double width) {
        this.name = ShapeType.RECTANGLE.getValue();
        this.height = height;
        this.width = width;
    }

    @Override
    public double getArea() {
        return this.height * this.width;
    }
}
