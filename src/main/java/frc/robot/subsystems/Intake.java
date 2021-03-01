package frc.robot.subsystems;

/**
 * Interface for the intake subsystem.
 *
 * @author Colin Robertson
 * @since 0.0.0
 */
public interface Intake {
    /**
     * Actuate the intake upwards.
     */
    void actuateUp();

    /**
     * Actuate the intake downwards.
     */
    void actuateDown();

    /**
     * Activate the intake's rollers.
     *
     * @param speed the speed the rollers should run at.
     */
    void intake(double speed);
}
