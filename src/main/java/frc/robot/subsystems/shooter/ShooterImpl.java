package frc.robot.subsystems.shooter;

public class ShooterImpl implements frc.robot.subsystems.Shooter {
    private final ShooterMotors motors;
    private final ShooterEncoders encoders;
    private final ShooterFlywheel flywheel;

    public ShooterImpl(int flywheelId) {
        this.motors = new ShooterMotors(flywheelId);
        this.encoders = new ShooterEncoders(motors);
        this.flywheel = new ShooterFlywheel(motors, encoders);
    }

    @Override
    public void shoot(double power) {
        flywheel.shoot(power);
    }

    @Override
    public double getVelocity() {
        return encoders.getFlywheelVelocity();
    }
}
