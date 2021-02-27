package frc.robot;

import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;

/**
 * Interface shared between drivetrains.
 *
 * @author Colin Robertson
 * @since 0.0.0
 */
public interface Drive {
    /**
     * Drive the robot.
     *
     * @param translation the drivetrain's translation. Translations are pairs
     *                    of X and Y values indicating how far in each
     *                    direction the vehicle should move.
     * @param rotation    the drivetrain's desired rotation. This rotation value
     *                    should normally be measured in radians per second,
     *                    but some of the math here is a bit funky.
     */
    void drive(Translation2d translation, Rotation2d rotation);
}
