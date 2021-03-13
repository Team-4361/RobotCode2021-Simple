package frc.robot.subsystems.swerve;

import edu.wpi.first.wpilibj.controller.PIDController;

public abstract class PidExample {
    private final static double maxError = 20;

    private final PIDController controller;

    {
        double kP = 1 / maxError;
        double kI = 0;
        double kD = 0;
        controller = new PIDController(kP, kI, kD) {{
            setSetpoint(0);
        }};
    }

    public void update(double degreesToTarget) {
        double calculatedPower = controller.calculate(degreesToTarget);

        turnRobot(calculatedPower);
    }

    public abstract void turnRobot(double power);
}
