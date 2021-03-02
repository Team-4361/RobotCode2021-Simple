package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.ShooterCommand;
import frc.robot.subsystems.shooter.ShooterImpl;

public class ShooterSubsystem extends Subsystem implements Shooter {
    private static ShooterSubsystem instance;
    private final Shooter shooter;

    public ShooterSubsystem(Shooter shooter) {
        this.shooter = shooter;
    }

    public static ShooterSubsystem getInstance() {
        if (instance == null) instance = new ShooterSubsystem(
                new ShooterImpl(
                        RobotMap.SHOOTER_FLYWHEEL
                )
        );

        return instance;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ShooterCommand());
    }

    @Override
    public void shoot(double power) {
        shooter.shoot(power);
    }

    @Override
    public double getVelocity() {
        return shooter.getVelocity();
    }
}
