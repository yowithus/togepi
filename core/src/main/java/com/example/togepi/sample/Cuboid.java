package com.example.togepi.sample;

import com.example.togepi.enums.ShapeType;

public class Cuboid extends AbstractShape implements SolidShape {

    private double height;
    private double width;
    private double length;

    public Cuboid(double height, double width, double length) {
        this.name = ShapeType.CUBOID.getValue();
        this.height = height;
        this.width = width;
        this.length = length;
    }

    @Override
    public double getArea() {
        return 2 * (this.height * this.width + this.width * this.length + this.length * this.height);
    }

    @Override
    public double getVolume() {
        return this.height * this.width * this.length;
    }
}
