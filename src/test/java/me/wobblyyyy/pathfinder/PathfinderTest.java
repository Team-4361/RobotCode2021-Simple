package me.wobblyyyy.pathfinder;

import me.wobblyyyy.pathfinder.api.Pathfinder;
import me.wobblyyyy.pathfinder.geometry.HeadingPoint;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class PathfinderTest {
    Pathfinder pathfinder;

    @Test
    public void testPathfinder() throws InterruptedException {
        long execPID = 0;
        long execTRA = 0;
        pathfinder = new Pathfinder(new CoolConfig());

        execTRA = System.currentTimeMillis();
        pathfinder.followPath(new ArrayList<>() {{
            add(new HeadingPoint(
                    0,
                    0,
                    0
            ));
            add(new HeadingPoint(
                    20,
                    30,
                    19
            ));
            add(new HeadingPoint(
                    78,
                    103,
                    24
            ));
            add(new HeadingPoint(
                    201,
                    201,
                    201
            ));
            add(new HeadingPoint(
                    0,
                    0,
                    0
            ));
            add(new HeadingPoint(
                    20,
                    30,
                    19
            ));
            add(new HeadingPoint(
                    78,
                    103,
                    24
            ));
            add(new HeadingPoint(
                    201,
                    201,
                    201
            ));
            add(new HeadingPoint(
                    0,
                    0,
                    0
            ));
            add(new HeadingPoint(
                    20,
                    30,
                    19
            ));
            add(new HeadingPoint(
                    78,
                    103,
                    24
            ));
            add(new HeadingPoint(
                    201,
                    201,
                    201
            ));
            add(new HeadingPoint(
                    0,
                    0,
                    0
            ));
            add(new HeadingPoint(
                    20,
                    30,
                    19
            ));
            add(new HeadingPoint(
                    78,
                    103,
                    24
            ));
            add(new HeadingPoint(
                    201,
                    201,
                    201
            ));
            add(new HeadingPoint(
                    0,
                    0,
                    0
            ));
            add(new HeadingPoint(
                    20,
                    30,
                    19
            ));
            add(new HeadingPoint(
                    78,
                    103,
                    24
            ));
            add(new HeadingPoint(
                    201,
                    201,
                    201
            ));
            add(new HeadingPoint(
                    0,
                    0,
                    0
            ));
            add(new HeadingPoint(
                    20,
                    30,
                    19
            ));
            add(new HeadingPoint(
                    78,
                    103,
                    24
            ));
            add(new HeadingPoint(
                    201,
                    201,
                    201
            ));
            add(new HeadingPoint(
                    0,
                    0,
                    0
            ));
            add(new HeadingPoint(
                    20,
                    30,
                    19
            ));
            add(new HeadingPoint(
                    78,
                    103,
                    24
            ));
            add(new HeadingPoint(
                    201,
                    201,
                    201
            ));
            add(new HeadingPoint(
                    0,
                    0,
                    0
            ));
            add(new HeadingPoint(
                    20,
                    30,
                    19
            ));
            add(new HeadingPoint(
                    78,
                    103,
                    24
            ));
            add(new HeadingPoint(
                    201,
                    201,
                    201
            ));
            add(new HeadingPoint(
                    0,
                    0,
                    0
            ));
            add(new HeadingPoint(
                    20,
                    30,
                    19
            ));
            add(new HeadingPoint(
                    78,
                    103,
                    24
            ));
            add(new HeadingPoint(
                    201,
                    201,
                    201
            ));
            add(new HeadingPoint(
                    0,
                    0,
                    0
            ));
            add(new HeadingPoint(
                    20,
                    30,
                    19
            ));
            add(new HeadingPoint(
                    78,
                    103,
                    24
            ));
            add(new HeadingPoint(
                    201,
                    201,
                    201
            ));
            add(new HeadingPoint(
                    0,
                    0,
                    0
            ));
            add(new HeadingPoint(
                    20,
                    30,
                    19
            ));
            add(new HeadingPoint(
                    78,
                    103,
                    24
            ));
            add(new HeadingPoint(
                    201,
                    201,
                    201
            ));
            add(new HeadingPoint(
                    0,
                    0,
                    0
            ));
            add(new HeadingPoint(
                    20,
                    30,
                    19
            ));
            add(new HeadingPoint(
                    78,
                    103,
                    24
            ));
            add(new HeadingPoint(
                    201,
                    201,
                    201
            ));
            add(new HeadingPoint(
                    0,
                    0,
                    0
            ));
            add(new HeadingPoint(
                    20,
                    30,
                    19
            ));
            add(new HeadingPoint(
                    78,
                    103,
                    24
            ));
            add(new HeadingPoint(
                    201,
                    201,
                    201
            ));
            add(new HeadingPoint(
                    0,
                    0,
                    0
            ));
            add(new HeadingPoint(
                    20,
                    30,
                    19
            ));
            add(new HeadingPoint(
                    78,
                    103,
                    24
            ));
            add(new HeadingPoint(
                    201,
                    201,
                    201
            ));
        }});
        execTRA = System.currentTimeMillis() - execTRA;

        System.out.println(execTRA);
    }
}
