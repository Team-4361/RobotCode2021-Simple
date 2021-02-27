package frc.robot.intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class IntakeMotors {
    private static final ControlMode MODE = ControlMode.PercentOutput;

    private static final double ACTUATOR_COEFFICIENT = 0.5;
    private static final double ROLLER_COEFFICIENT = 0.5;

    private final TalonSRX actuator;
    private final TalonSRX roller;

    private double actuatorSpeed = 0;
    private double rollerSpeed = 0;

    public IntakeMotors(int actuatorId,
                        int rollerId) {
        actuator = new TalonSRX(actuatorId);
        roller = new TalonSRX(rollerId);
    }

    public TalonSRX getActuator() {
        return actuator;
    }

    public TalonSRX getRoller() {
        return roller;
    }

    private double calculateActuatorSpeed(double input) {
        return input * ACTUATOR_COEFFICIENT;
    }

    private double calculateRollerSpeed(double input) {
        return input * ROLLER_COEFFICIENT;
    }

    public void setActuatorSpeed(double speed) {
        speed = calculateActuatorSpeed(speed);
        actuator.set(MODE, speed);
        this.actuatorSpeed = speed;
    }

    public void setRollerSpeed(double speed) {
        speed = calculateRollerSpeed(speed);
        roller.set(MODE, speed * ROLLER_COEFFICIENT);
        this.rollerSpeed = speed;
    }

    public double getActuatorSpeed() {
        return this.actuatorSpeed;
    }

    public double getRollerSpeed() {
        return this.rollerSpeed;
    }
}
