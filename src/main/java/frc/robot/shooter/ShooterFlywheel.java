package frc.robot.shooter;

public class ShooterFlywheel {
    private final ShooterMotors motors;

    public ShooterFlywheel(ShooterMotors motors) {
        this.motors = motors;
    }

    public void shoot(double speed) {
        motors.setFlywheelSpeed(speed);
    }
}
