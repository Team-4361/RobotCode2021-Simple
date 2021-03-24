package frc.robot.util;

import frc.robot.auton.Autonomous;
import me.wobblyyyy.edt.DynamicArray;
import me.wobblyyyy.pathfinder.geometry.HeadingPoint;
import me.wobblyyyy.pathfinder.geometry.Point;
import org.junit.jupiter.api.Test;

public class TestCurves {
    @Test
    public void getCurvePoints() {
        DynamicArray<HeadingPoint> q1 = Curves.increasing(Curves.interpolatedQ1);
        DynamicArray<HeadingPoint> q2 = Curves.increasing(Curves.interpolatedQ2);
        DynamicArray<HeadingPoint> q3 = Curves.increasing(Curves.interpolatedQ3);
        DynamicArray<HeadingPoint> q4 = Curves.increasing(Curves.interpolatedQ4);

        System.out.println("Q1");
        q1.itr().forEach(point -> {
            System.out.println(point.toString());
        });
        System.out.println();

        System.out.println("Q2");
        q2.itr().forEach(point -> {
            System.out.println(point.toString());
        });
        System.out.println();

        System.out.println("Q3");
        q3.itr().forEach(point -> {
            System.out.println(point.toString());
        });
        System.out.println();

        System.out.println("Q4");
        q4.itr().forEach(point -> {
            System.out.println(point.toString());
        });
        System.out.println();
    }

    @Test
    public void testRelative() {
        DynamicArray<HeadingPoint> q1 = Curves.increasing(Curves.interpolatedQ1);

        Point center = new Point(15, 20);
        double radius = 10;

        DynamicArray<HeadingPoint> transformed = Curves.from(q1, radius, center);

        transformed.itr().forEach(point -> {
            System.out.println(point.toString());
        });
    }

    @Test
    public void printBarrelPath() {
        Autonomous.barrelPath.itr().forEach(point -> {
            System.out.println(point.toString());
        });
    }
}
