package frc.robot.auton;

import frc.robot.subsystems.swerve.chassis.SwerveChassis;
import me.wobblyyyy.edt.DynamicArray;
import me.wobblyyyy.pathfinder.api.Pathfinder;
import me.wobblyyyy.pathfinder.config.SimpleConfig;
import me.wobblyyyy.pathfinder.core.Followers;
import me.wobblyyyy.pathfinder.geometry.HeadingPoint;
import me.wobblyyyy.pathfinder.maps.frc.EmptyFRC;

public class PathfinderImpl {
    public SimpleConfig config;
    public Pathfinder pathfinder;

    public static DynamicArray<HeadingPoint> rectangle20 = mutate(new DynamicArray<>(
        new HeadingPoint(0, 20, 0),
        new HeadingPoint(20, 20, 0),
        new HeadingPoint(20, 0, 0),
        new HeadingPoint(0, 0, 0)
    ), false, true);
    public static DynamicArray<HeadingPoint> rectangle40 = mutate(new DynamicArray<>(
        new HeadingPoint(0, 40, 0),
        new HeadingPoint(40, 40, 0),
        new HeadingPoint(40, 0, 0),
        new HeadingPoint(0, 0, 0)
    ), false, true);
    public static DynamicArray<HeadingPoint> rectangle120 = mutate(new DynamicArray<>(
        new HeadingPoint(0, 0, 0),
        new HeadingPoint(120, 0, 0),
        new HeadingPoint(120, 120, 0),
        new HeadingPoint(0, 120, 0),
        new HeadingPoint(0, 0, 0)
    ), false, true);
    public static DynamicArray<HeadingPoint> star20 = mutate(new DynamicArray<>(
        new HeadingPoint(0, 20, 0),
        new HeadingPoint(20, 20, 0),
        new HeadingPoint(20, 0, 0),
        new HeadingPoint(0, 0, 0),
        new HeadingPoint(10, 20, 0),
        new HeadingPoint(20, 20, 0),
        new HeadingPoint(20, 10, 0),
        new HeadingPoint(10, 10, 0),
        new HeadingPoint(0, 0, 0)
    ), false, true);
    public static DynamicArray<HeadingPoint> forwards10 = mutate(new DynamicArray<>(
        new HeadingPoint(0, 0, 0),
        new HeadingPoint(0, 10, 0)
    ), false, true);
    public static DynamicArray<HeadingPoint> forwards120 = mutate(new DynamicArray<>(
        new HeadingPoint(0, 0, 0),
        new HeadingPoint(120, 0, 0)
    ), false, true);
    public static DynamicArray<HeadingPoint> backwards120 = mutate(new DynamicArray<>(
        new HeadingPoint(0, 0, 0),
        new HeadingPoint(120, 0, 0)
    ), false, true);
    public static DynamicArray<HeadingPoint> forwards240 = mutate(new DynamicArray<>(
        new HeadingPoint(0, 0, 0),
        new HeadingPoint(240, 0, 0)
    ), false, true);
    public static DynamicArray<HeadingPoint> forwardsBackwards = mutate(new DynamicArray<>(
        new HeadingPoint(0, 0, 0),
        new HeadingPoint(120, 0, 0),
        new HeadingPoint(0.1, 0.1, 0),
        new HeadingPoint(120, 0, 0),
        new HeadingPoint(0.1, 0.1, 0)
    ), false, true);
    public static DynamicArray<HeadingPoint> backwardsForwardsBackwardsForwards = mutate(new DynamicArray<> (
        new HeadingPoint(0, 0, 0),
        new HeadingPoint(-120, 0, 0),
        new HeadingPoint(0, 0, 0),
        new HeadingPoint(-120, 0, 0)
    ), false, true);
    // x and y values are switched
    // each point needs to be "unique", meaning you can't have -60 and -60
    // you have to have something like -60.1 -60.2 etc etc
    // you can just suffix every number with a decimal point that's different
    // from the last one and all your problems will go away! magical!
    public static DynamicArray<HeadingPoint> slamonPath = mutate(new DynamicArray<>(
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
    public static DynamicArray<HeadingPoint> backwardsSlamonPath = mutate(new DynamicArray<>(
        new HeadingPoint(0.1, 0.1, 0)
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

    public PathfinderImpl(SwerveChassis chassis) {
        config = new SimpleConfig() {{
            setDrive(chassis);
            setOdometry(chassis.getOdometry());
            setFollower(Followers.LINEAR);
            setGapX(23.5);
            setGapY(23.5);
            setMap(new EmptyFRC());
            setSpeed(0.225);
            swapXY(false);
            invertX(false);
            invertY(false);
        }};
        pathfinder = new Pathfinder(config);
    }

    public Pathfinder getPathfinder() {
        return pathfinder;
    }
}
