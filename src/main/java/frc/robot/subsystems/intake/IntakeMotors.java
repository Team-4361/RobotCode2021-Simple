package frc.robot.subsystems.intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Constants;
import me.wobblyyyy.intra.ftc2.utils.math.Math;

public class IntakeMotors {
    private static final ControlMode MODE = ControlMode.PercentOutput;

    private static final double ACTUATOR_COEFFICIENT = 1.0;
    private static final double ROLLER_COEFFICIENT = 0.75;

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
        return Math.clip(input * ACTUATOR_COEFFICIENT);
    }

    private double calculateRollerSpeed(double input) {
        return Math.clip(
                Constants.INTAKE_CLIP * -1,
                Constants.INTAKE_CLIP * 1,
                input * ROLLER_COEFFICIENT
        );
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
