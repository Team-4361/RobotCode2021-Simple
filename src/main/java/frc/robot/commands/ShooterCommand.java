package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

        SmartDashboard.putNumber(
                "Velocity",
                ShooterSubsystem.getInstance().getVelocity()
        );
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
