package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;
// import frc.robot.util.PathDrawTest;

public final class Main {
    public static void main(String... args) {
        // uncomment to test paths
        // i can't figure out how this ide works because it wasn't
        // designed for java development, this should normally be
        // in a junit test instead of a main method
        // don't uncomment this unless you want to draw a path
        // PathDrawTest test = new PathDrawTest();
        // test.drawBarrelPath();
        // try {
            // Thread.sleep(10000000);
        // } catch (Exception e) { e.printStackTrace(); }
        RobotBase.startRobot(Robot::new);
    }
}
