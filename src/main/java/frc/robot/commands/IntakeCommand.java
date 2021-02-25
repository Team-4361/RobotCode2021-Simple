package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCommand extends Command {
    public IntakeCommand() {
        requires(IntakeSubsystem.getInstance());
    }

    @Override
    protected void execute() {
        if (!true) {
        // Out intake power
        double out = -Robot.getOi()
                .getController()
                .getTriggerAxis(GenericHID.Hand.kLeft);
        // In intake power
        double in = Robot.getOi()
                .getController()
                .getTriggerAxis(GenericHID.Hand.kRight);

        // Combine in and out, gets the intake's power.
        double intakePower = out + in;

        IntakeSubsystem.getInstance().startIntake(intakePower);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
