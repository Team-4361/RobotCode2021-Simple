package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCommand extends Command {
    public IntakeCommand() {
        requires(IntakeSubsystem.getInstance());
    }

    private void actuatorControl() {
        boolean intakeUp = Robot.getIo().getIntakeUp();
        boolean intakeDown = Robot.getIo().getIntakeDown();

        if (intakeUp) IntakeSubsystem.getInstance().actuateUp();
        else if (intakeDown) IntakeSubsystem.getInstance().actuateDown();
    }

    private void rollerControl() {
        double I = Robot.getIo().getIntake();
        double O = Robot.getIo().getOuttake();
        double power = I - O;
        
        IntakeSubsystem.getInstance().intake(power);
    }

    @Override
    protected void execute() {
        actuatorControl();
        rollerControl();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
