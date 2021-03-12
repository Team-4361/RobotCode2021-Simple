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
        new HeadingPoint(0, 10, 0),
        new HeadingPoint(0, 0, 0)
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
            swapXY(true);
            invertX(false);
            invertY(true);
        }};
        pathfinder = new Pathfinder(config);
    }

    public Pathfinder getPathfinder() {
        return pathfinder;
    }
}
