package frc.robot.auton;

import frc.robot.util.Arcs;
import frc.robot.util.Curves;
import me.wobblyyyy.edt.DynamicArray;
import me.wobblyyyy.pathfinder.api.Pathfinder;
import me.wobblyyyy.pathfinder.geometry.AngleUtils;
import me.wobblyyyy.pathfinder.geometry.HeadingPoint;
import me.wobblyyyy.pathfinder.geometry.Point;
import me.wobblyyyy.pathfinder.util.Distance;

/**
 * Autonomous utility for the robot's 2021 season.
 * 
 * <p>
 * Due to some messy code required to use wpilib's kinematics instead
 * of my own, the paths need to be inputted like this.
 * <ol>
 *   <li>Call the mutate method on the path with the order false, true</li>
 *   <li>All of the points you input are in this order: Y, X, HEADING</li>
 *   <li>The X points you input have to be inverted</li>
 * </ol>
 * </p>
 * 
 * <p>
 * <pre>
 * Left: negative X
 * Right: positive X
 * Forwards: positive Y
 * Backwards: negative Y
 * </pre>
 * </p>
 */
public class Autonomous {
    /*
     * 4/1/2021 - colin
     * 
     * jordan reported issues with the autonomous, reportedly didn't
     * work and would move to different points every time it was ran.
     * this may have to do with automatic point correction to bypass
     * pathfinder's merge algorithm. this project still uses pathfinder
     * v0.3.0, the latest version is v0.6.1. the merge algorithm has
     * since been updated to remove the need to differentiate points
     * from eachother. we can upgrade to v0.6.1 but need some time to
     * refactor and re-test the drive class - it uses RTransform instead
     * of translations and rotations, etc. i removed the automatic
     * point differentiation code as of now (it used dynamic array iterator
     * index functionality, which would appear to work fine) but
     * we should look into this in the future.
     */

    public static DynamicArray<HeadingPoint> rectangleTest = mutate(new DynamicArray<>(
        // y, x
        new HeadingPoint(0.001, 0.001, 0.001),
        new HeadingPoint(40.002, 0.002, 0.002),
        new HeadingPoint(40.003, 40.003, 0.003),
        new HeadingPoint(0.004, 40.004, 0.004),
        new HeadingPoint(0.005, 0.005, 0.005)
    ), false, true);
    
    // Slalom Autonomous Points
    public static DynamicArray<HeadingPoint> slalomPath = mutate(new DynamicArray<>(
            new HeadingPoint(10.1, 0.1, 0),
            new HeadingPoint(60.2, -90.2, 0),
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
    public static DynamicArray<HeadingPoint> barrelRacingPath = swap(new DynamicArray<>() {{
        Point D5 = new Point(30, 90);
        Point B8 = new Point(-30, 180);
        Point D10 = new Point(30, 240);

        // follow circles with a radius of 24 inches to ensure clearence
        add(Distance.inDirection(D5, 90, 24).withHeading(0));
        add(Distance.inDirection(D5, 0, 24).withHeading(0));
        add(Distance.inDirection(D5, AngleUtils.fixDeg(-90), 24).withHeading(0));
        add(Distance.inDirection(D5, AngleUtils.fixDeg(-180), 24).withHeading(0));
        add(Distance.inDirection(D5, 90, 24).withHeading(0));

        add(Distance.inDirection(B8, AngleUtils.fixDeg(-90), 24).withHeading(0));
        add(Distance.inDirection(B8, AngleUtils.fixDeg(0), 24).withHeading(0));
        add(Distance.inDirection(B8, AngleUtils.fixDeg(90), 24).withHeading(0));
        add(Distance.inDirection(B8, AngleUtils.fixDeg(-180), 24).withHeading(0));

        // NOT FINISHED YET PLZ ADD MORE L8R
    }});

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
    public static final DynamicArray<HeadingPoint> barrelPath =
                swap(new DynamicArray<>() {{
                    final Point D5_CENTER = new Point(-30, 120);
                    final double D5_RADIUS = 40;
                    final Point B8_CENTER = new Point(30, 210);
                    final double B8_RADIUS = 40;
                    final Point D10_CENTER = new Point(-30, 270);
                    final double D10_RADIUS = 40;

                    DynamicArray<HeadingPoint> D5_A = Arcs.decreasingFrom(
                            Arcs.INTERPOLATED_QUAD_1, D5_RADIUS, D5_CENTER);
                    DynamicArray<HeadingPoint> D5_B = Arcs.decreasingFrom(
                            Arcs.INTERPOLATED_QUAD_2, D5_RADIUS, D5_CENTER);
                    DynamicArray<HeadingPoint> D5_C = Arcs.increasingFrom(
                            Arcs.INTERPOLATED_QUAD_3, D5_RADIUS, D5_CENTER);
                    DynamicArray<HeadingPoint> D5_D = Arcs.increasingFrom(
                            Arcs.INTERPOLATED_QUAD_4, D5_RADIUS, D5_CENTER);

                    DynamicArray<HeadingPoint> B8_A = Arcs.increasingFrom(
                            Arcs.INTERPOLATED_QUAD_2, B8_RADIUS, B8_CENTER);
                    DynamicArray<HeadingPoint> B8_B = Arcs.increasingFrom(
                            Arcs.INTERPOLATED_QUAD_1, B8_RADIUS, B8_CENTER);
                    DynamicArray<HeadingPoint> B8_C = Arcs.decreasingFrom(
                            Arcs.INTERPOLATED_QUAD_4, B8_RADIUS, B8_CENTER);

                    DynamicArray<HeadingPoint> D10_A = new DynamicArray<>(
                            new HeadingPoint(-30, 230, 0));
                    DynamicArray<HeadingPoint> D10_B = Arcs.increasingFrom(
                            Arcs.INTERPOLATED_QUAD_2, D10_RADIUS, D10_CENTER);
                    DynamicArray<HeadingPoint> D10_C = Arcs.increasingFrom(
                            Arcs.INTERPOLATED_QUAD_1, D10_RADIUS, D10_CENTER);

                    DynamicArray<HeadingPoint> RETURN_TO_START = new DynamicArray<>(
                            new HeadingPoint(0, 220, 0),
                            new HeadingPoint(10, 120, 0),
                            new HeadingPoint(0, 0, 0)
                    );

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
                }});

    // Bounce Path Autonomous Points
    // updated but not tested as of 4/1/2021
    public static DynamicArray<HeadingPoint> bouncePath = swap(new DynamicArray<>(
        new HeadingPoint(-60, 30, 0),
        new HeadingPoint(30, 30, 0),
        new HeadingPoint(60, 60, 0),
        new HeadingPoint(30, 90, 0),
        new HeadingPoint(-60, 90, 0),
        new HeadingPoint(30, 90, 0),
        new HeadingPoint(60, 90, 0),
        new HeadingPoint(60, 135, 0),
        new HeadingPoint(60, 180, 0),
        new HeadingPoint(30, 180, 0),
        new HeadingPoint(-60, 180, 0),
        new HeadingPoint(10, 240, 0),
        new HeadingPoint(0, 240, 0)
    ));

    public static DynamicArray<HeadingPoint> lightspeedCircuitPath = mutate(swap(new DynamicArray<>(
        new HeadingPoint(-30.1, 0.1, 0),
        new HeadingPoint(-60.2, 0.2, 0),
        new HeadingPoint(-60.3, 30.3, 0),
        new HeadingPoint(-60.4, 60.4, 0),
        new HeadingPoint(-60.5, 90.5, 0),
        new HeadingPoint(-30.6, 90.6, 0),
        new HeadingPoint(-30.7, 120.5, 0),
        new HeadingPoint(0.8, 120.8, 0),
        new HeadingPoint(0.9, 150.9, 0),
        new HeadingPoint(0.01, 175.01, 0),
        new HeadingPoint(-30.02, 175.02, 0),
        new HeadingPoint(-30.03, 210.03, 0),
        new HeadingPoint(-60.04, 210.04, 0),
        new HeadingPoint(-60.05, 240.05, 0),
        new HeadingPoint(-30.06, 240.06, 0),
        new HeadingPoint(-30.07, 270.07, 0),
        new HeadingPoint(0.08, 270.08, 0),
        new HeadingPoint(30.09, 270.09, 0),
        new HeadingPoint(30.001, 240.001, 0),
        new HeadingPoint(30.002, 175.002, 0),
        new HeadingPoint(30.003, 120.003, 0),
        new HeadingPoint(30.004, 90.004, 0),
        new HeadingPoint(0.005, 90.005, 0),
        new HeadingPoint(0.006, 30.006, 0),
        new HeadingPoint(30.007, 30.007, 0),
        new HeadingPoint(30.008, 0.008, 0)
    )), false, true);

    public static DynamicArray<HeadingPoint> swap(
            DynamicArray<HeadingPoint> points) {
        DynamicArray<HeadingPoint> newPoints = new DynamicArray<>();
        points.itr().forEach(point -> {
            double x = point.getY();
            double y = point.getX();
            // see the comment in the mutate() method
            // dynamic array iteration might be broken, but that doesn't
            // really make sense to me, previous tests i ran demonstrated
            // it worked fine. if we need to do auto point
            // differentation, we can later
            // x += points.itr().index() / 1000;
            // y += points.itr().index() / 1000;
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
            double x = !invertX ? point.getX() : point.getX() * -1;
            double y = !invertY ? point.getY() : point.getY() * -1;
            // does adding the index over 1000 change anything?
            // it shouldnt, esp bc it's static and should be calculated
            // at compile time instead of runtime
            // if point differentiation doesn't work, figure out a work
            // around for this. edt might have a bug, but it seems
            // unlikely to me - previous unit tests didn't reveal any issues
            // x += points.itr().index() / 1000;
            // y += points.itr().index() / 1000;
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
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
