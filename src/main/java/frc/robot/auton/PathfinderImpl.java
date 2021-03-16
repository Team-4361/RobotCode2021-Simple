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

    public static DynamicArray<HeadingPoint> rectangle20 = new DynamicArray<>(
        new HeadingPoint(0, 20, 0),
        new HeadingPoint(20, 20, 0),
        new HeadingPoint(20, 0, 0),
        new HeadingPoint(0, 0, 0)
    );
    public static DynamicArray<HeadingPoint> rectangle40 = new DynamicArray<>(
        new HeadingPoint(0, 40, 0),
        new HeadingPoint(40, 40, 0),
        new HeadingPoint(40, 0, 0),
        new HeadingPoint(0, 0, 0)
    );
    public static DynamicArray<HeadingPoint> rectangle120 = new DynamicArray<>(
        new HeadingPoint(0, 0, 0),
        new HeadingPoint(120, 0, 0),
        new HeadingPoint(120, 120, 0),
        new HeadingPoint(0, 120, 0),
        new HeadingPoint(0, 0, 0)
    );
    public static DynamicArray<HeadingPoint> star20 = new DynamicArray<>(
        new HeadingPoint(0, 20, 0),
        new HeadingPoint(20, 20, 0),
        new HeadingPoint(20, 0, 0),
        new HeadingPoint(0, 0, 0),
        new HeadingPoint(10, 20, 0),
        new HeadingPoint(20, 20, 0),
        new HeadingPoint(20, 10, 0),
        new HeadingPoint(10, 10, 0),
        new HeadingPoint(0, 0, 0)
    );
    public static DynamicArray<HeadingPoint> forwards10 = new DynamicArray<>(
        new HeadingPoint(0, 0, 0),
        new HeadingPoint(0, 10, 0)
    );
    public static DynamicArray<HeadingPoint> forwards20 = new DynamicArray<>(
        new HeadingPoint(0, 0, 0),
        new HeadingPoint(240, 0, 0)
    );

    public PathfinderImpl(SwerveChassis chassis) {
        config = new SimpleConfig() {{
            setDrive(chassis);
            setOdometry(chassis.getOdometry());
            setFollower(Followers.LINEAR);
            setGapX(23.5);
            setGapY(23.5);
            setMap(new EmptyFRC());
            setSpeed(0.3);
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
