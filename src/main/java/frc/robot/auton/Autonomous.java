package frc.robot.auton;

import me.wobblyyyy.edt.DynamicArray;
import me.wobblyyyy.pathfinder.api.Pathfinder;
import me.wobblyyyy.pathfinder.geometry.HeadingPoint;

public class Autonomous {

// Slalom Autonomous Points
    public static DynamicArray<HeadingPoint> slalomPath = mutate(new DynamicArray<>(
        new HeadingPoint(10.1, 0.1, 0),
        new HeadingPoint(72.2, -90.2, 0),
        new HeadingPoint(210.3, -90.3, 0),
        new HeadingPoint(210.4, 4.4, 0),
        new HeadingPoint(290.5, 4.5, 0),
        new HeadingPoint(290.6, -90.6, 0),
        new HeadingPoint(210.7, -90.7, 0),
        new HeadingPoint(210.8, 4.8, 0),
        new HeadingPoint(72.9, 4.9, 0),
        new HeadingPoint(20.1, -60.1, 0),
        new HeadingPoint(0.2, -60.2, 0)
    ), false, true); 

    // Barrell Racing Path Autonomous Points - Not updated
    public static DynamicArray<HeadingPoint> barrelRacingPath = mutate(new DynamicArray<>(
        new HeadingPoint(10.1, 0.1, 0),
        new HeadingPoint(72.2, -90.2, 0),
        new HeadingPoint(210.3, -90.3, 0),
        new HeadingPoint(210.4, 4.4, 0),
        new HeadingPoint(290.5, 4.5, 0),
        new HeadingPoint(290.6, -90.6, 0),
        new HeadingPoint(210.7, -90.7, 0),
        new HeadingPoint(210.8, 4.8, 0),
        new HeadingPoint(72.9, 4.9, 0),
        new HeadingPoint(20.1, -60.1, 0),
        new HeadingPoint(0.2, -60.2, 0)
    ), false, true); 

    // Bounce Path Autonomous Points - Not updated
    public static DynamicArray<HeadingPoint> bouncePath = mutate(new DynamicArray<>(
        new HeadingPoint(10.1, 0.1, 0),
        new HeadingPoint(72.2, -90.2, 0),
        new HeadingPoint(210.3, -90.3, 0),
        new HeadingPoint(210.4, 4.4, 0),
        new HeadingPoint(290.5, 4.5, 0),
        new HeadingPoint(290.6, -90.6, 0),
        new HeadingPoint(210.7, -90.7, 0),
        new HeadingPoint(210.8, 4.8, 0),
        new HeadingPoint(72.9, 4.9, 0),
        new HeadingPoint(20.1, -60.1, 0),
        new HeadingPoint(0.2, -60.2, 0)
    ), false, true); 
    public static DynamicArray<HeadingPoint> mutate(DynamicArray<HeadingPoint> points, boolean invertX, boolean invertY) {
        DynamicArray<HeadingPoint> newPoints = new DynamicArray<>(points.size());
        points.itr().forEach(point -> {
            final double x = !invertX ? point.getX() : point.getX() * -1;
            final double y = !invertY ? point.getY() : point.getY() * -1;
            newPoints.add(new HeadingPoint(x, y, point.getHeading()));
        });
        return newPoints;
    }

    public static void execute(PathfinderImpl impl, DynamicArray<HeadingPoint> points) {
        Pathfinder pathfinder = impl.getPathfinder();
        pathfinder.open();
        pathfinder.waitForAndStop(
            pathfinder.followPath(points)
        );
        pathfinder.close();
    }
}
