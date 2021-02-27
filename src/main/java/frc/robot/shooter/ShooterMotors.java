package frc.robot.shooter;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class ShooterMotors {
    private static final ControlMode MODE = ControlMode.PercentOutput;

    private static final double FLYWHEEL_COEFFICIENT = 0.5;

    private final TalonSRX flywheel;

    private double flywheelSpeed = 0;

    public ShooterMotors(int flywheelId) {
        flywheel = new TalonSRX(flywheelId);
    }

    private double calculateFlywheelSpeed(double input) {
        return input * FLYWHEEL_COEFFICIENT;
    }

    public void setFlywheelSpeed(double speed) {
        speed = calculateFlywheelSpeed(speed);
        flywheel.set(MODE, speed);
        this.flywheelSpeed = speed;
    }

    public double getFlywheelSpeed() {
        return flywheelSpeed;
    }
}
