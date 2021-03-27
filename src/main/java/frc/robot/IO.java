package frc.robot;

import frc.robot.controls.ControlScheme;
import frc.robot.controls.ControlSchemes;

/**
 * Wrapper class for control schemes.
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

    
    @Override
    public boolean getA() {
        return scheme.getA();
    }

    @Override
    public boolean getB() {
        return scheme.getB();
    }

    @Override
    public boolean getX() {
        return scheme.getX();
    }

    @Override
    public boolean getY() {
        return scheme.getY();
    }
}
