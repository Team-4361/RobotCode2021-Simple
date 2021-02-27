package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.subsystems.DrivetrainSubsystem;

public class OI {
    /*
       Add your joysticks and buttons here
     */
    private Joystick primaryJoystick = new Joystick(0);
    private Joystick secondaryJoystick = new Joystick(1);
    private XboxController controller = new XboxController(2);

    public OI() {
        // Back button zeroes the drivetrain
        new JoystickButton(primaryJoystick, 7).whenPressed(
                new InstantCommand(() -> DrivetrainSubsystem.getInstance().resetGyroscope())
        );
    }

    public Joystick getPrimaryJoystick() {
        return primaryJoystick;
    }

    public Joystick getSecondaryJoystick() {
        return secondaryJoystick;
    }

    public XboxController getController() {
        return controller;
    }

    public boolean getButton(Buttons b) {
        switch (b) {
            case A: return controller.getAButton();
            case B: return controller.getBButton();
            case X: return controller.getXButton();
            case Y: return controller.getYButton();
        }

        return false;
    }

    public boolean getPressed(Buttons b) {
        switch (b) {
            case A: return controller.getAButtonPressed();
            case B: return controller.getBButtonPressed();
            case X: return controller.getXButtonPressed();
            case Y: return controller.getYButtonPressed();
        }

        return false;
    }

    public double getTrigger(Hands h) {
        switch (h) {
            case L: return controller.getTriggerAxis(GenericHID.Hand.kLeft);
            case R: return controller.getTriggerAxis(GenericHID.Hand.kRight);
        }

        return 0.0;
    }

    public boolean getBumper(Hands h) {
        switch (h) {
            case L: return controller.getBumper(GenericHID.Hand.kLeft);
            case R: return controller.getBumper(GenericHID.Hand.kRight);
        }

        return false;
    }

    public boolean getBumperPressed(Hands h) {
        switch (h) {
            case L: return controller.getBumperPressed(GenericHID.Hand.kLeft);
            case R: return controller.getBumperPressed(GenericHID.Hand.kRight);
        }

        return false;
    }

    public enum Buttons {
        A,
        B,
        X,
        Y
    }

    public enum Hands {
        L,
        R
    }
}
