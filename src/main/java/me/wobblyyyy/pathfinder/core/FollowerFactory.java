package me.wobblyyyy.pathfinder.core;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import me.wobblyyyy.edt.DynamicArray;
import me.wobblyyyy.pathfinder.config.PathfinderConfig;
import me.wobblyyyy.pathfinder.followers.DualPidFollower;
import me.wobblyyyy.pathfinder.followers.LinearFollower;
import me.wobblyyyy.pathfinder.followers.TriPidFollower;
import me.wobblyyyy.pathfinder.geometry.HeadingPoint;

public class FollowerFactory {
    public static DynamicArray<HeadingPoint> applyInvertAndSwap(PathfinderConfig config, 
                                                                DynamicArray<HeadingPoint> points) {
        DynamicArray<HeadingPoint> newPoints = new DynamicArray<>();

        points.itr().forEach(point -> {
            double x = config.swapXY() ? point.getY() : point.getX();
            double y = config.swapXY() ? point.getX() : point.getY();
            x = config.invertX() ? -x : x;
            y = config.invertY() ? -y : y;

            newPoints.add(new HeadingPoint(x, y, point.getHeading()));
        });

        return newPoints;
    }

    public static DualPidFollower dualPid(PathfinderConfig config,
                                          DynamicArray<HeadingPoint> points) {
        points = applyInvertAndSwap(config, points);
        return new DualPidFollower(
                config.getDrive(),
                config.getOdometry(),
                points.get(1),
                config.getSpeed()
        );
    }

    public static TriPidFollower triPid(PathfinderConfig config,
                                        DynamicArray<HeadingPoint> points) {
        points = applyInvertAndSwap(config, points);
        return new TriPidFollower(
                config.getDrive(),
                config.getOdometry(),
                points.get(1),
                config.getSpeed()
        );
    }

    public static LinearFollower linear(PathfinderConfig config,
                                        DynamicArray<HeadingPoint> points) {
        points = applyInvertAndSwap(config, points);
        return new LinearFollower(
                config.getDrive(),
                config.getOdometry(),
                points.get(0),
                points.get(1),
                config.getSpeed()
        );
    }
}
