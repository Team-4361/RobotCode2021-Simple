package frc.robot.shooter;

public class Shooter implements frc.robot.subsystems.Shooter {
    private final ShooterMotors motors;
    private final ShooterFlywheel flywheel;

    public Shooter(int flywheelId) {
        this.motors = new ShooterMotors(flywheelId);

        flywheel = new ShooterFlywheel(motors);
    }

    @Override
    public void shoot(double power) {
        flywheel.shoot(power);
    }
}
