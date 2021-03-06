package frc.robot.subsystems.swerve.motor;

import me.wobblyyyy.pathfinder.robot.Motor;

/**
 * Interface to be implemented by all motors used in the swerve drivetrain.
 *
 * @author Colin Robertson
 * @since 0.0.0
 */
public interface SwerveMotor extends Motor {
    /**
     * Set power to the motor.
     *
     * @param power the power value to set to the motor.
     */
    void setPower(double power);

    /**
     * Get the motor's power.
     *
     * @return the motor's power.
     */
    double getPower();
}
