package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.StorageSubsystem;

public class ShooterCommand extends Command {
    private static final double TARGET_A = 0;
    private static final double TARGET_B = 0;
    private static final double TARGET_X = 0;
    private static final double TARGET_Y = 0;
    private static final double TARGET = 0;

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

    private double getStoragePower(double vel) {
        final double localTarget;

        if (Robot.getIo().getA()) localTarget = TARGET_A;
        else if (Robot.getIo().getB()) localTarget = TARGET_B;
        else if (Robot.getIo().getX()) localTarget = TARGET_X;
        else if (Robot.getIo().getY()) localTarget = TARGET_Y;
        else localTarget = TARGET;
        
        if (vel > localTarget) return 0.5;
        return 0.0;
    }

    private void powerStorage() {
        double velocity = Math.abs(ShooterSubsystem.getInstance().getVelocity());

        StorageSubsystem.getInstance().move(getStoragePower(velocity));
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
