package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterCommand extends Command {
    public ShooterCommand() {
        requires(ShooterSubsystem.getInstance());
    }

    private void powerShooter() {
        double power = Robot.getOi().getTrigger(OI.Hands.R);

        ShooterSubsystem.getInstance().shoot(power);
    }

    @Override
    protected void execute() {
        powerShooter();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
