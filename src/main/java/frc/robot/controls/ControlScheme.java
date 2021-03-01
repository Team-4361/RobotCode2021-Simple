package frc.robot.controls;

/**
 * Interface used in standardizing different control schemes and making
 * it easier to switch between them.
 *
 * <p>
 * Unless you don't have much programming experience, it makes no sense to
 * have a single hard-coded class for all controls. Anyone who drives the robot
 * will want to have controls customized to their likings. Using an interface
 * as a supplier and a class as a wrapper allows for on-the-fly control
 * scheme switching and updating, without having to worry about messing any of
 * the other controls up.
 * </p>
 *
 * @author Colin Robertson
 * @since 0.0.0
 */
public interface ControlScheme {
    /**
     * Desired swerve drive forwards power.
     *
     * <p>
     * This is most often something like joystick Y.
     * </p>
     *
     * @return desired forwards power.
     */
    double getF();

    /**
     * Desired swerve drive strafe power.
     *
     * @return desired swerve drive strafe power.
     */
    double getS();

    /**
     * Desired swerve drive turn power.
     *
     * @return desired swerve drive turn power.
     */
    double getT();

    /**
     * Should the storage indexer be enabled?
     *
     * @return should the storage indexer be enabled or not.
     */
    boolean getStorage();

    /**
     * Should the shooter be enabled?
     *
     * @return should the shooter be enabled?
     */
    boolean getShooter();

    /**
     * Get the intake's intaking power.
     *
     * @return the intake's intaking power.
     */
    double getIntake();

    /**
     * Get the intake's outtaking power.
     *
     * @return the intake's outtaking power.
     */
    double getOuttake();

    /**
     * Should the intake be going up?
     *
     * @return whether or not the intake should be going up.
     */
    boolean getIntakeUp();

    /**
     * Should the intake be going down?
     *
     * @return whether or not the intake should be going down.
     */
    boolean getIntakeDown();
}
