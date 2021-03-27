package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.StorageSubsystem;

public class ShooterCommand extends Command {
    private static final double aPower = .865; //green zone
    private static final double yPower = .745; //yellow zone
    private static final double xPower = .795; // blue zone
    private static final double bPower = .875; // red zone
    

    private static final double TARGET_A = 5500 * aPower;
    private static final double TARGET_B = 5500 * bPower;
    private static final double TARGET_X = 5500 * xPower;
    private static final double TARGET_Y = 5500 * yPower;
    private static final double TARGET = 1000000;

    public ShooterCommand() {
        requires(ShooterSubsystem.getInstance());
        requires(StorageSubsystem.getInstance());
    }

    private void powerShooter() {
        final double localTarget;

        if (Robot.getIo().getA()) localTarget = aPower;
        else if (Robot.getIo().getB()) localTarget = bPower;
        else if (Robot.getIo().getX()) localTarget = xPower;
        else if (Robot.getIo().getY()) localTarget = yPower;
        else localTarget = 0.0;

        ShooterSubsystem.getInstance().shoot(
            localTarget
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
