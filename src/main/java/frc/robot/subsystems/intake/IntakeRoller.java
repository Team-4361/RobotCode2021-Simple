package frc.robot.subsystems.intake;

public class IntakeRoller {
    private final IntakeMotors motors;

    public IntakeRoller(IntakeMotors motors) {
        this.motors = motors;
    }

    public void intake(double speed) {
        motors.setRollerSpeed(speed);
    }
}
