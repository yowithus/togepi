package com.example.togepi.sample;

import com.example.togepi.enums.ShapeType;

public class ShapeFactory {

    public static Shape getShape(ShapeType shapeType, double... params) {
        switch (shapeType) {
            case CIRCLE:
                return new Circle(params[0]);
            case RECTANGLE:
                return new Rectangle(params[0], params[1]);
            case TRIANGLE:
                return new Triangle(params[0], params[1]);
            case CUBOID:
                return new Cuboid(params[0], params[1], params[2]);
            case SPHERE:
                return new Sphere(params[0]);
            default:
                return new Circle(params[0]);
        }
    }
}
