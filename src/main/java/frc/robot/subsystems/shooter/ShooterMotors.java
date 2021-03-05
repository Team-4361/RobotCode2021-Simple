package frc.robot.subsystems.shooter;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class ShooterMotors {
    private static final CANSparkMaxLowLevel.MotorType TYPE = MotorType.kBrushless;
    private static final IdleMode MODE = IdleMode.kCoast;

    private static final double FLYWHEEL_COEFFICIENT = 1.0;

    private final CANSparkMax flywheel;

    private double flywheelSpeed = 0;

    public ShooterMotors(int flywheelId) {
        flywheel = new CANSparkMax(flywheelId, TYPE);
        flywheel.setIdleMode(MODE);
    }

    public CANSparkMax getFlywheel() {
        return flywheel;
    }

    private double calculateFlywheelSpeed(double input) {
        return input * FLYWHEEL_COEFFICIENT;
    }

    public void setFlywheelSpeed(double speed) {
        speed = calculateFlywheelSpeed(speed);
        flywheel.set(speed);
        this.flywheelSpeed = speed;
    }

    public double getFlywheelSpeed() {
        return flywheelSpeed;
    }
}
