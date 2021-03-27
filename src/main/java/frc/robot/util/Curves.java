package frc.robot.util;

import me.wobblyyyy.edt.DynamicArray;
import me.wobblyyyy.edt.StaticArray;
import me.wobblyyyy.pathfinder.geometry.HeadingPoint;
import me.wobblyyyy.pathfinder.geometry.Point;
import me.wobblyyyy.pathfinder.trajectory.PathGenerator;
import me.wobblyyyy.pathfinder.trajectory.Spline;
import me.wobblyyyy.pathfinder.trajectory.SplineInterpolator;
import me.wobblyyyy.pathfinder.trajectory.Trajectory;
import me.wobblyyyy.pathfinder.util.Distance;

/**
 * Use spline interpolation to create basic curves.
 *
 * <p>
 * If you're confused about what's going on here, check out the files that
 * are linked with the "see" tags. Control-click on them and it'll bring you
 * to documentation explaining some of the internals.
 * </p>
 *
 * @see SplineInterpolator
 * @see Spline
 * @see Trajectory
 * @see PathGenerator#toPath(Trajectory)
 */
public class Curves {
    public static final HeadingPoint origin = new HeadingPoint(0, 0, 0);

    public static final HeadingPoint quadrantalU = new HeadingPoint(+0, +1, +0);
    public static final HeadingPoint quadrantalR = new HeadingPoint(+1, +0, +0);
    public static final HeadingPoint quadrantalD = new HeadingPoint(+0, -1, +0);
    public static final HeadingPoint quadrantalL = new HeadingPoint(-1, +0, +0);

    public static final HeadingPoint midQ1 = Distance
            .inDirection(origin, 45, 1)
            .withHeading(0);
    public static final HeadingPoint midQ2 = Distance
            .inDirection(origin, 135, 1)
            .withHeading(0);
    public static final HeadingPoint midQ3 = Distance
            .inDirection(origin, 225, 1)
            .withHeading(0);
    public static final HeadingPoint midQ4 = Distance
            .inDirection(origin, 315, 1)
            .withHeading(0);

    // use monotone cubic spline interpolation
    // all splines go left to right
    // increasing() and decreasing() to modify
    public static final Spline splineQ1 = new Spline(new StaticArray<>(
            quadrantalU,
            midQ1,
            quadrantalR
    ));
    public static final Spline splineQ2 = new Spline(new StaticArray<>(
            quadrantalL,
            midQ2,
            quadrantalU
    ));
    public static final Spline splineQ3 = new Spline(new StaticArray<>(
            quadrantalL,
            midQ3,
            quadrantalD
    ));
    public static final Spline splineQ4 = new Spline(new StaticArray<>(
            quadrantalD,
            midQ4,
            quadrantalR
    ));

    public static final Trajectory trajectoryQ1 = new Trajectory(
            new StaticArray<>(splineQ1));
    public static final Trajectory trajectoryQ2 = new Trajectory(
            new StaticArray<>(splineQ2));
    public static final Trajectory trajectoryQ3 = new Trajectory(
            new StaticArray<>(splineQ3));
    public static final Trajectory trajectoryQ4 = new Trajectory(
            new StaticArray<>(splineQ4));

    /**
     * The amount of samples to use for each path. Samples are best defined
     * as how many points each curve will have.
     */
    public static final int SAMPLES = 40;

    public static final DynamicArray<HeadingPoint> interpolatedQ1 =
            PathGenerator.toPath(trajectoryQ1, SAMPLES);
    public static final DynamicArray<HeadingPoint> interpolatedQ2 =
            PathGenerator.toPath(trajectoryQ2, SAMPLES);
    public static final DynamicArray<HeadingPoint> interpolatedQ3 =
            PathGenerator.toPath(trajectoryQ3, SAMPLES);
    public static final DynamicArray<HeadingPoint> interpolatedQ4 =
            PathGenerator.toPath(trajectoryQ4, SAMPLES);

    /**
     * Ensure all of the provided points are in increasing X order.
     *
     * @param points the set of points you'd like to use.
     * @return the points in increasing X order.
     */
    public static DynamicArray<HeadingPoint> increasing(
            DynamicArray<HeadingPoint> points) {
        double fX = points.get(0).getX();
        double lX = points.get(points.size() - 1).getX();

        if (fX > lX) {
            // original is decreasing
            DynamicArray<HeadingPoint> newPoints = new DynamicArray<>();
            points.itr().forEach(point -> {
                newPoints.add(0, point);
            });
            return newPoints;
        } else {
            // original is increasing
            return points;
        }
    }

    /**
     * Ensure all of the provided points are in decreasing X order.
     *
     * @param points the set of points you'd like to use.
     * @return the points in decreasing X order.
     */
    public static DynamicArray<HeadingPoint> decreasing(
            DynamicArray<HeadingPoint> points) {
        double fX = points.get(0).getX();
        double lX = points.get(points.size() - 1).getX();

        if (fX < lX) {
            // original is increasing
            DynamicArray<HeadingPoint> newPoints = new DynamicArray<>();
            points.itr().forEach(point -> {
                newPoints.add(0, point);
            });
            return newPoints;
        } else {
            // original is decreasing
            return points;
        }
    }

    /**
     * Scale a set of points.
     *
     * @param points the points to scale.
     * @param scale  the multiplier to multiply each of the points by
     * @return a scaled set of points.
     */
    public static DynamicArray<HeadingPoint> scale(
            DynamicArray<HeadingPoint> points,
            double scale) {
        return new DynamicArray<>() {{
            points.itr().forEach(point -> {
                add(HeadingPoint.scale(point, scale));
            });
        }};
    }

    /**
     * Add a center point to each of the entries in the provided array.
     *
     * @param points the array of points that will be displaced.
     * @param center the desired center point.
     * @return displaced points.
     */
    public static DynamicArray<HeadingPoint> withCenter(
            DynamicArray<HeadingPoint> points,
            Point center) {
        return new DynamicArray<>() {{
            points.itr().forEach(point -> {
                add(Point.add(point, center).withHeading(0));
            });
        }};
    }

    /**
     * Transform an inputted set of points based on the provided radius
     * and center point. The entire array of points is scaled up using the
     * radius as a multiplier. Then, the entire array gets the center point
     * added to it, thus making an arc around the center point.
     *
     * @param points the points to scale and move.
     * @param radius the radius of the arc.
     * @param center the center point of the arc.
     * @return a fresh set of transformed points.
     * @see #scale(DynamicArray, double)
     * @see #withCenter(DynamicArray, Point)
     */
    public static DynamicArray<HeadingPoint> from(
            DynamicArray<HeadingPoint> points,
            double radius,
            Point center) {
        DynamicArray<HeadingPoint> scaled = scale(points, radius);

        return withCenter(scaled, center);
    }

    /**
     * Apply the from operation and then apply the increasing operation.
     *
     * @param points the points that should be transformed.
     * @param radius the radius of the arc.
     * @param center the center point of the arc.
     * @return a newly-transformed curve.
     * @see #increasing(DynamicArray)
     * @see #from(DynamicArray, double, Point)
     */
    public static DynamicArray<HeadingPoint> increasingFrom(
            DynamicArray<HeadingPoint> points,
            double radius,
            Point center) {
        return increasing(from(points, radius, center));
    }

    /**
     * Apply the from operation and then apply the decreasing operation.
     *
     * @param points the points that should be transformed.
     * @param radius the radius of the arc.
     * @param center the center point of the arc.
     * @return a newly-transformed curve.
     * @see #decreasing(DynamicArray)
     * @see #from(DynamicArray, double, Point)
     */
    public static DynamicArray<HeadingPoint> decreasingFrom(
            DynamicArray<HeadingPoint> points,
            double radius,
            Point center) {
        return decreasing(from(points, radius, center));
    }
}
