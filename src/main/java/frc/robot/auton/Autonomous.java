package frc.robot.auton;

import frc.robot.util.Curves;
import me.wobblyyyy.edt.DynamicArray;
import me.wobblyyyy.pathfinder.api.Pathfinder;
import me.wobblyyyy.pathfinder.geometry.HeadingPoint;
import me.wobblyyyy.pathfinder.geometry.Point;

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

    // Barrel Racing Path Autonomous Points - Not updated
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

    /**
     * Untested set of points for the barrel path.
     *
     * <p>
     * This path uses monotone cubic spline interpolation to generate points
     * along theoretical splines/arcs. If you're interested in how that works,
     * click {@link me.wobblyyyy.pathfinder.trajectory.SplineInterpolator} here.
     * </p>
     *
     * <p>
     * Each of the method calls has some documentation explaining what it does.
     * I believe they can be control-clicked and your IDE will jump right to
     * the method you're looking at.
     * </p>
     *
     * <p>
     * All of the points in this path are notated as X, Y, where positive X
     * is to the right of the robot (if it's facing forwards) and positive Y
     * is in front of the robot (once again, facing forwards). The points are
     * mutated and swapped before being passed to the robot.
     * </p>
     *
     * @see me.wobblyyyy.pathfinder.trajectory.PathGenerator
     * @see me.wobblyyyy.pathfinder.trajectory.Spline
     * @see me.wobblyyyy.pathfinder.trajectory.SplineInterpolator
     */
    public static DynamicArray<HeadingPoint> barrelPath = mutate(
            /*
             * We use an anonymous class initializer here to make the code
             * more readable.
             */
            swap(new DynamicArray<>() {{
                /*
                 * Set up center points and radius measurements.
                 *
                 * These radius measurements are done in inches, as are the
                 * points and everything else.
                 */

                final Point D5_CENTER = new Point(0, 150);
                final double D5_RADIUS = 24;
                final Point B8_CENTER = new Point(120, 240);
                final double B8_RADIUS = 24;
                final Point D10_CENTER = new Point(0, 300);
                final double D10_RADIUS = 24;

                DynamicArray<HeadingPoint> D5_A = Curves.increasingFrom(
                        Curves.interpolatedQ2, D5_RADIUS, D5_CENTER);
                DynamicArray<HeadingPoint> D5_B = Curves.increasingFrom(
                        Curves.interpolatedQ1, D5_RADIUS, D5_CENTER);
                DynamicArray<HeadingPoint> D5_C = Curves.decreasingFrom(
                        Curves.interpolatedQ4, D5_RADIUS, D5_CENTER);
                DynamicArray<HeadingPoint> D5_D = Curves.decreasingFrom(
                        Curves.interpolatedQ3, D5_RADIUS, D5_CENTER);

                DynamicArray<HeadingPoint> B8_A = Curves.decreasingFrom(
                        Curves.interpolatedQ1, B8_RADIUS, B8_CENTER);
                DynamicArray<HeadingPoint> B8_B = Curves.decreasingFrom(
                        Curves.interpolatedQ2, B8_RADIUS, B8_CENTER);
                DynamicArray<HeadingPoint> B8_C = Curves.increasingFrom(
                        Curves.interpolatedQ3, B8_RADIUS, B8_CENTER);

                DynamicArray<HeadingPoint> D10_A = new DynamicArray<>(
                        new HeadingPoint(0, 275, 0));
                DynamicArray<HeadingPoint> D10_B = Curves.decreasingFrom(
                        Curves.interpolatedQ1, D10_RADIUS, D10_CENTER);
                DynamicArray<HeadingPoint> D10_C = Curves.decreasingFrom(
                        Curves.interpolatedQ2, D10_RADIUS, D10_CENTER);

                DynamicArray<HeadingPoint> RETURN_TO_START = new DynamicArray<>(
                        new HeadingPoint(24, 275, 0),
                        new HeadingPoint(22, 150, 0),
                        new HeadingPoint(20, 0, 0)
                );

                /*
                 * Add all of the points to the array, in order.
                 *
                 * D5 to B8 to D10 to the start zone.
                 */

                D5_A.itr().forEach(this::add);
                D5_B.itr().forEach(this::add);
                D5_C.itr().forEach(this::add);
                D5_D.itr().forEach(this::add);
                B8_A.itr().forEach(this::add);
                B8_B.itr().forEach(this::add);
                B8_C.itr().forEach(this::add);
                D10_A.itr().forEach(this::add);
                D10_B.itr().forEach(this::add);
                D10_C.itr().forEach(this::add);
                RETURN_TO_START.itr().forEach(this::add);
            }}),   // end class initializer
            false, // don't swap x
            false  // don't swap y
    ); // end field declaration

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

    public static DynamicArray<HeadingPoint> swap(
            DynamicArray<HeadingPoint> points) {
        DynamicArray<HeadingPoint> newPoints = new DynamicArray<>();
        points.itr().forEach(point -> {
            final double x = point.getY();
            final double y = point.getX();
            newPoints.add(new HeadingPoint(x, y, point.getHeading()));
        });
        return newPoints;
    }

    public static DynamicArray<HeadingPoint> mutate(
            DynamicArray<HeadingPoint> points,
            boolean invertX,
            boolean invertY) {
        DynamicArray<HeadingPoint> newPoints = new DynamicArray<>();
        points.itr().forEach(point -> {
            final double x = !invertX ? point.getX() : point.getX() * -1;
            final double y = !invertY ? point.getY() : point.getY() * -1;
            newPoints.add(new HeadingPoint(x, y, point.getHeading()));
        });
        return newPoints;
    }

    public static void execute(PathfinderImpl impl,
                               DynamicArray<HeadingPoint> points) {
        Pathfinder pathfinder = impl.getPathfinder();
        pathfinder.open();
        pathfinder.waitForAndStop(
                pathfinder.followPath(points)
        );
        pathfinder.close();
    }
}
