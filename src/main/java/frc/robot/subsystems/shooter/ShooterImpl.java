package frc.robot.subsystems.shooter;

public class ShooterImpl implements frc.robot.subsystems.Shooter {
    private final ShooterMotors motors;
    private final ShooterFlywheel flywheel;

    public ShooterImpl(int flywheelId) {
        this.motors = new ShooterMotors(flywheelId);

        flywheel = new ShooterFlywheel(motors);
    }

    @Override
    public void shoot(double power) {
        flywheel.shoot(power);
    }
}
