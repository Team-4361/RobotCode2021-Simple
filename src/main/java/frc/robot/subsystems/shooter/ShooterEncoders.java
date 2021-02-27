package frc.robot.subsystems.shooter;

import com.revrobotics.CANEncoder;

public class ShooterEncoders {
    private final ShooterMotors motors;

    private final CANEncoder flywheelEncoder;

    public ShooterEncoders(ShooterMotors motors) {
        this.motors = motors;

        flywheelEncoder = motors.getFlywheel().getEncoder();
    }

    public double getFlywheelVelocity() {
        return flywheelEncoder.getVelocity();
    }
}
