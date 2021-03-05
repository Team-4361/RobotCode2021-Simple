package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.StorageSubsystem;

public class ShooterCommand extends Command {
    public ShooterCommand() {
        requires(ShooterSubsystem.getInstance());
        requires(StorageSubsystem.getInstance());
    }

    private void powerShooter() {
        ShooterSubsystem.getInstance().shoot(
                Robot.getIo().getShooter() ?
                        1.0 :
                        0.0
        );
    }

    private void powerStorage() {
        double velocity = Math.abs(ShooterSubsystem.getInstance().getVelocity());

        if (velocity > 5500) {
            if (Robot.getIo().getShooter()) {
                StorageSubsystem.getInstance().move(0.5);
            }
        } else StorageSubsystem.getInstance().move(0.0);
    }

    @Override
    protected void execute() {
        powerShooter();
        powerStorage();

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
