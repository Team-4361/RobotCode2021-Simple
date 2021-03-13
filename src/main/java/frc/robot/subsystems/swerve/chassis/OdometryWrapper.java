package frc.robot.subsystems.swerve.chassis;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import me.wobblyyyy.intra.ftc2.utils.math.Math;
import me.wobblyyyy.pathfinder.geometry.HeadingPoint;
import me.wobblyyyy.pathfinder.kinematics.SwerveOdometry;
import me.wobblyyyy.pathfinder.robot.Odometry;
import me.wobblyyyy.pathfinder.util.Distance;

public class OdometryWrapper implements Odometry {
    private SwerveOdometry odometry;
    private SwerveDriveOdometry internal;
    private double gyroAngle = 0;
    private SwerveModuleState[] states;

    public OdometryWrapper(SwerveDriveOdometry internal) {
        this.internal = internal;
    }

    public Pose2d getPoseMeters() {
        return internal.getPoseMeters();
    }

    @Override
    public HeadingPoint getPos() {
        Pose2d poseMeters = getPoseMeters();
        // double x = Distance.METERS_TO_INCHES * poseMeters.getX();
        // double y = Distance.METERS_TO_INCHES * poseMeters.getY();
        double x = poseMeters.getX();
        double y = poseMeters.getY();
        double z = Math.toDegrees(poseMeters.getRotation().getRadians());
        return new HeadingPoint(x, y, z);
    }

    public double toRps(double rpm) {
        return rpm / 60;
    }

    public double[] toRps(double[] vs) {
        return new double[] {
            toRps(vs[0]),
            toRps(vs[1]),
            toRps(vs[2]),
            toRps(vs[3])
        };
    } 

    // 8.16 equals 12.566
    // 12.566/8.16 (distance per rotation)
    public double toDistance(double rps, double diameter, double ratio) {
        double distancePerRotation = (diameter * Math.PI) / ratio;

        return rps * distancePerRotation;
    }

    /**
     * @param rps rotations per second array
     * @return distance array
     */
    public double[] toDistance(double[] rps, double diameter, double ratio) {
        return new double[] {
            toDistance(rps[0], diameter, ratio),
            toDistance(rps[1], diameter, ratio),
            toDistance(rps[2], diameter, ratio),
            toDistance(rps[3], diameter, ratio),
        };
    }

    public double toMeters(double inches) {
        return inches * 0.0254;
    }

    public double[] toMeters(double[] inches) {
        return new double[] {
            toMeters(inches[0]),
            toMeters(inches[1]),
            toMeters(inches[2]),
            toMeters(inches[3]),
        };
    }

    /**
     * @param states     all of the swerve module states - FL FR BL BR
     * @param velocities an array of velocities, each measured in ROTATIONS PER
     * MINUTE. These velocites can be acquired with the getVelocity() method.
     */
    public void updateRobot(SwerveModuleState[] states, double[] velocities) {
        velocities[1] *= -1; // invert FR, it's backwards
        velocities[3] *= -1; // invert BR, it's backwards
        double[] rps = toRps(velocities); // convert velocities (rpm) to rotations per sec
        double[] ips = toDistance(rps, 4.0, 8.16); // convert rps to inches per second
        double[] mps = ips; // reassignment, stop using MPS, use IPS

        // logging stuff! heck yeah!
        SmartDashboard.putNumberArray("rps", rps);
        SmartDashboard.putNumberArray("ips", ips);

        // create new states, arrays are mutable, we can't set the states
        // of the previous array so we have to make a new one
        SwerveModuleState[] fixedStates = new SwerveModuleState[] {
            new SwerveModuleState(mps[0], states[0].angle),
            new SwerveModuleState(mps[1], states[1].angle),
            new SwerveModuleState(mps[2], states[2].angle),
            new SwerveModuleState(mps[3], states[3].angle),
        };

        // update the internal states
        updateStates(fixedStates);
    }

    private void updateVelocities(double frV, double flV, double brV, double blV) {

    }

    private void updateStates(SwerveModuleState[] states) {
        this.states = states;
    }

    public void updateAngle(double angle) {
        gyroAngle = angle;
    }

    @Override
    public void update() {
        try {
            internal.update(Rotation2d.fromDegrees(gyroAngle), states);
        } catch (Exception ignored) {
            // ignored.printStackTrace();
        }
    }
}
