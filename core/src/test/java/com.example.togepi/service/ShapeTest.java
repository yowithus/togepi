package com.example.togepi.service;

import com.example.togepi.enums.ShapeType;
import com.example.togepi.sample.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShapeTest {

    @Test
    public void givenCircle_whenCalculatingArea_thenReturnCorrectResult() throws Exception {
        final double radius = 10;
        final double area = Math.PI * Math.pow(radius, 2);
        final Shape circle = ShapeFactory.getShape(ShapeType.CIRCLE, radius);
        assertEquals(circle.getArea(), area, 0.02);
    }

    @Test
    public void givenRectangle_whenCalculatingArea_thenReturnCorrectResult() throws Exception {
        final double height = 10;
        final double width = 10;
        final double area = height * width;
        final Shape rectangle = ShapeFactory.getShape(ShapeType.RECTANGLE, height, width);
        assertEquals(rectangle.getArea(), area, 0.02);
    }

    @Test
    public void givenTriangle_whenCalculatingArea_thenReturnCorrectResult() throws Exception {
        final double base = 10;
        final double height = 10;
        final double area = height * base / 2;
        final Shape triangle = ShapeFactory.getShape(ShapeType.TRIANGLE, base, height);
        assertEquals(triangle.getArea(), area, 0.02);
    }

    @Test
    public void givenCuboid_whenCalculatingAreaAndVolume_thenReturnCorrectResult() throws Exception {
        final double height = 10;
        final double width = 10;
        final double length = 10;
        final double area = 2 * (height * width + width * length + length * height);
        final double volume = height * width * length;
        final SolidShape cuboid = (SolidShape)ShapeFactory.getShape(ShapeType.CUBOID, height, width, length);
        assertEquals(cuboid.getArea(), area, 0.02);
        assertEquals(cuboid.getVolume(), volume, 0.02);
    }

    @Test
    public void givenSphere_whenCalculatingAreaAndVolume_thenReturnCorrectResult() throws Exception {
        final double radius = 10;
        final double area = 4 * Math.PI * Math.pow(radius, 2);;
        final double volume = 4/3 * Math.PI * Math.pow(radius, 3);
        final SolidShape sphere = (SolidShape)ShapeFactory.getShape(ShapeType.SPHERE, radius);
        assertEquals(sphere.getArea(), area, 0.02);
        assertEquals(sphere.getVolume(), volume, 0.02);
    }
}
