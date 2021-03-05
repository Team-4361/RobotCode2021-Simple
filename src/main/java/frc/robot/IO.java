package frc.robot;

import frc.robot.controls.ControlScheme;
import frc.robot.controls.ControlSchemes;

/**
 * Wrapper class for control schemes.
 *
 * <p>
 * The way controls were originally set up in this codebase was great, if
 * and only if you only ever wanted to use a singular control scheme. Which,
 * in this case, we don't. Rather, we'd like to use however many different
 * control schemes we would like to use.
 * </p>
 *
 * <p>
 * It would be a tremendous waste of time to focus exclusively on a single
 * control scheme. The best way I could describe it would be "absolutely
 * pointless" - not allowing for the development of multiple control schemes
 * will greatly hinder the ability to switch out controls for whatever reason.
 * </p>
 *
 * <p>
 * Say, for example, we're testing drivers on their ability to drive the robot.
 * Of course, it would be incredibly moronic to force every single person to
 * drive the robot the exact same way - how would that test skill, if the
 * driver didn't feel comfortable driving the robot in the first place?
 * </p>
 *
 * @author Colin Robertson
 * @since 0.0.0
 */
public class IO implements ControlScheme {
    /**
     * The internally-used control scheme.
     *
     * <p>
     * This can be changed on-the-fly to whatever you'd like. As Colin, I
     * obviously prefer the Colin scheme - however, the driver of the robot
     * should ultimately get the final say in terms of how the robot is
     * controlled.
     * </p>
     *
     * <p>
     * The documentation right here might not be very clear. I'm not about
     * to type an entire Wikipedia article here, so the relevant documentation
     * and information is linked here.
     * </p>
     *
     * @see ControlScheme
     * @see ControlSchemes
     * @see Constants#scheme
     */
    private final ControlScheme scheme = Constants.scheme.getScheme();

    /**
     * Desired swerve drive forwards power.
     *
     * <p>
     * This is most often something like joystick Y.
     * </p>
     *
     * @return desired forwards power.
     */
    @Override
    public double getF() {
        return scheme.getF();
    }

    /**
     * Desired swerve drive strafe power.
     *
     * @return desired swerve drive strafe power.
     */
    @Override
    public double getS() {
        return scheme.getS();
    }

    /**
     * Desired swerve drive turn power.
     *
     * @return desired swerve drive turn power.
     */
    @Override
    public double getT() {
        return scheme.getT();
    }

    /**
     * Should the storage indexer be enabled?
     *
     * @return should the storage indexer be enabled or not.
     */
    @Override
    public boolean getStorage() {
        return scheme.getStorage();
    }

    /**
     * Should the shooter be enabled?
     *
     * @return should the shooter be enabled?
     */
    @Override
    public boolean getShooter() {
        return scheme.getShooter();
    }

    /**
     * Get the intake's intaking power.
     *
     * @return the intake's intaking power.
     */
    @Override
    public double getIntake() {
        return scheme.getIntake();
    }

    /**
     * Get the intake's outtaking power.
     *
     * @return the intake's outtaking power.
     */
    @Override
    public double getOuttake() {
        return scheme.getOuttake();
    }

    /**
     * Should the intake be going up?
     *
     * @return whether or not the intake should be going up.
     */
    @Override
    public boolean getIntakeUp() {
        return scheme.getIntakeUp();
    }

    /**
     * Should the intake be going down?
     *
     * @return whether or not the intake should be going down.
     */
    @Override
    public boolean getIntakeDown() {
        return scheme.getIntakeDown();
    }
}
