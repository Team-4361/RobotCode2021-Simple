package frc.robot.swerve.encoder;

/**
 * Core interface for various types of encoders.
 *
 * <p>
 * We won't only ever be using just Spark encoders - ideally, we'll have
 * other encoders used for the drive wheels, allowing us to track the robot.
 * </p>
 *
 * @author Colin Robertson
 * @since 0.0.0
 */
public interface SwerveEncoder {
    double getPos();
    int getCpr();
}
