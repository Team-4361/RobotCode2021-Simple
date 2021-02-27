package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCommand extends Command {
    private boolean aPressedLast = false;
    private boolean bPressedLast = false;

    public IntakeCommand() {
        requires(IntakeSubsystem.getInstance());
    }

    @Override
    protected void execute() {
        boolean aPressed = Robot.getOi().getController().getAButton();
        boolean bPressed = Robot.getOi().getController().getBButton();
        aPressedLast = aPressed;
        bPressedLast = bPressed;
        double intakeSpeed = Robot.getOi().getController().getX(GenericHID.Hand.kLeft);

        if (!aPressedLast && aPressed) {
            IntakeSubsystem.getInstance().actuateUp();
        } else if (!bPressedLast && bPressed) {
            IntakeSubsystem.getInstance().actuateDown();
        }

        IntakeSubsystem.getInstance().intake(intakeSpeed);
//        if (!true) {
//         Out intake power
//        double out = -Robot.getOi()
//                .getController()
//                .getTriggerAxis(GenericHID.Hand.kLeft);
//         In intake power
//        double in = Robot.getOi()
//                .getController()
//                .getTriggerAxis(GenericHID.Hand.kRight);
//
//         Combine in and out, gets the intake's power.
//        double intakePower = out + in;
//
//        IntakeSubsystem.getInstance().startIntake(intakePower);
//        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
