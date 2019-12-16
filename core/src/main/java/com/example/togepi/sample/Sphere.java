package com.example.togepi.sample;

import com.example.togepi.enums.ShapeType;

public class Sphere extends AbstractShape implements SolidShape {

    private double radius;

    public Sphere(double radius) {
        this.name = ShapeType.SPHERE.getValue();
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return 4 * Math.PI * Math.pow(this.radius, 2);
    }

    @Override
    public double getVolume() {
        return 4/3 * Math.PI * Math.pow(this.radius, 3);
    }
}
