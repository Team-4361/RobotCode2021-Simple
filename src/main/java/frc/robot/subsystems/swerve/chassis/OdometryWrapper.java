package frc.robot.subsystems.swerve.chassis;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import me.wobblyyyy.intra.ftc2.utils.math.Math;
import me.wobblyyyy.pathfinder.geometry.HeadingPoint;
import me.wobblyyyy.pathfinder.robot.Odometry;
import me.wobblyyyy.pathfinder.util.Distance;

public class OdometryWrapper implements Odometry {
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
        double x = Distance.METERS_TO_INCHES * poseMeters.getX();
        double y = Distance.METERS_TO_INCHES * poseMeters.getY();
        double z = Math.toDegrees(poseMeters.getRotation().getRadians());
        return new HeadingPoint(x, y, z);
    }

    public void updateStates(SwerveModuleState[] states) {
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
