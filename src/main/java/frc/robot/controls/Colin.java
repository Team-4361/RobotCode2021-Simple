package frc.robot.controls;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

/**
 * Very simple control scheme designed for ease-of-use in development.
 *
 * <p>
 * In other words, this is what I'd use if I were to drive the robot myself.
 * It doesn't really matter if you like these controls or not - they're very
 * easily hot-swappable. It would be pointless to require me to use a control
 * scheme I'm not comfortable with, especially on somewhat of a time crunch.
 * </p>
 *
 * @author Colin Robertson
 * @since 0.0.0
 */
public class Colin implements ControlScheme {
    /**
     * Left stick.
     */
    private final Joystick stick1;

    /**
     * Right stick.
     */
    private final Joystick stick2;

    /**
     * Xbox controller.
     */
    private final XboxController controller;

    /**
     * Create a new generic control scheme provider.
     *
     * @param stick1     the left stick's port.
     * @param stick2     the right stick's port.
     * @param controller the controller's port.
     */
    public Colin(int stick1,
                 int stick2,
                 int controller) {
        this.stick1 = new Joystick(stick1);
        this.stick2 = new Joystick(stick2);
        this.controller = new XboxController(controller);
    }

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
        return -controller.getY(GenericHID.Hand.kRight);
    }

    /**
     * Desired swerve drive strafe power.
     *
     * @return desired swerve drive strafe power.
     */
    @Override
    public double getS() {
        return -controller.getX(GenericHID.Hand.kRight);
    }

    /**
     * Desired swerve drive turn power.
     *
     * @return desired swerve drive turn power.
     */
    @Override
    public double getT() {
        return -controller.getX(GenericHID.Hand.kLeft);
    }

    /**
     * Should the storage indexer be enabled?
     *
     * @return should the storage indexer be enabled or not.
     */
    @Override
    public boolean getStorage() {
        return controller.getBumper(GenericHID.Hand.kLeft);
    }

    /**
     * Should the shooter be enabled?
     *
     * @return should the shooter be enabled?
     */
    @Override
    public boolean getShooter() {
        return controller.getBumper(GenericHID.Hand.kRight);
    }

    /**
     * Get the intake's intaking power.
     *
     * @return the intake's intaking power.
     */
    @Override
    public double getIntake() {
        return controller.getTriggerAxis(GenericHID.Hand.kRight);
    }

    /**
     * Get the intake's outtaking power.
     *
     * @return the intake's outtaking power.
     */
    @Override
    public double getOuttake() {
        return controller.getTriggerAxis(GenericHID.Hand.kLeft);
    }

    /**
     * Should the intake be going up?
     *
     * @return whether or not the intake should be going up.
     */
    @Override
    public boolean getIntakeUp() {
        return controller.getAButton();
    }

    /**
     * Should the intake be going down?
     *
     * @return whether or not the intake should be going down.
     */
    @Override
    public boolean getIntakeDown() {
        return controller.getBButton();
    }

    @Override
    public boolean getA() {
        return controller.getAButton();
    }

    @Override
    public boolean getB() {
        return controller.getBButton();
    }

    @Override
    public boolean getX() {
        return controller.getXButton();
    }

    @Override
    public boolean getY() {
        return controller.getYButton();
    }
}
