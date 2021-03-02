package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.subsystems.ShooterSubsystem;

public class ShooterCommand extends Command {
    public ShooterCommand() {
        requires(ShooterSubsystem.getInstance());
    }

    private void powerShooter() {
        ShooterSubsystem.getInstance().shoot(
                Robot.getIo().getShooter() ?
                        1.0 :
                        0.0
        );
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
