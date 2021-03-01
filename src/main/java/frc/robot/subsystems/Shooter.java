package frc.robot.subsystems;

/**
 * Interface for the robot's shooter.
 *
 * @author Colin Robertson
 * @since 0.0.0
 */
public interface Shooter {
    /**
     * Shoot the shooter.
     *
     * <p>
     * Following some updates, the shooter now uses a PID with a target
     * velocity instead of manual user input. This way, it shoots with as
     * close to the same velocity every single time.
     * </p>
     */
    void shoot();

    /**
     * Get the shooter's velocity.
     *
     * @return the shooter's velocity.
     */
    double getVelocity();
}
