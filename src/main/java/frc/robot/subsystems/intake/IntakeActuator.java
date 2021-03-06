package frc.robot.subsystems.intake;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeActuator {
    private static final String THREAD_NAME = "IntakeActuator";

    private enum ActuationTarget {
        TOP,
        BOTTOM,
        REST
    }

    private final TalonSRX actuatorMotor;
    private final IntakeLimits limits;
    private final Thread actuatorThread;
    private ActuationTarget actuationTarget = ActuationTarget.REST;

    public IntakeActuator(IntakeMotors motors,
                          IntakeLimits limits) {
        this.actuatorMotor = motors.getActuator();
        this.limits = limits;

        this.actuatorThread = new Thread(() -> {
            do {
                Thread.onSpinWait();

                SmartDashboard.putNumber("power", getActuationPower());

                motors.setActuatorSpeed(getActuationPower());

                // stopActuation();
            } while (true);

            // motors.setActuatorSpeed(getActuationPower());
        }, THREAD_NAME);

        actuatorThread.start();
    }

    private void startActuation() {
        
    }

    private boolean isActuationDone() {
        switch (actuationTarget) {
            case TOP:
                return limits.atTop();
            case BOTTOM:
                return limits.atBottom();
            default:
                return true;
        }
    }

    private double getActuationPower() {
        switch (actuationTarget) {
            case TOP:
                return 1.0;
            case BOTTOM:
                return -1.0;
            default:
                return 0.0;
        }
    }

    public void actuateUp() {
        actuationTarget = ActuationTarget.TOP;
        startActuation();
    }

    public void actuateDown() {
        actuationTarget = ActuationTarget.BOTTOM;
        startActuation();
    }

    public void stopActuation() {
        actuationTarget = ActuationTarget.REST;
    }
}
