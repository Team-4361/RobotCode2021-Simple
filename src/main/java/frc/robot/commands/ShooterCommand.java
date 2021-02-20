package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterCommand extends Command {
    public ShooterCommand() {
        requires(ShooterSubsystem.getInstance());
    }

    @Override
    protected void execute() {
        double shooterPower = Robot.getOi().getController().getAButton() ? 1 : 0;

        ShooterSubsystem.getInstance().shoot(shooterPower);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
