package frc.robot.intake;

public class Intake implements frc.robot.subsystems.Intake {
    private final IntakeLimits limits;
    private final IntakeMotors motors;
    private final IntakeActuator actuator;
    private final IntakeRoller roller;

    public Intake(int actuatorId,
                  int rollerId,
                  int topChannel,
                  int bottomChannel) {
        this.limits = new IntakeLimits(topChannel, bottomChannel);
        this.motors = new IntakeMotors(actuatorId, rollerId);

        this.actuator = new IntakeActuator(motors, limits);
        this.roller = new IntakeRoller(motors);
    }

    @Override
    public void actuateUp() {
        actuator.actuateUp();
    }

    @Override
    public void actuateDown() {
        actuator.actuateDown();
    }

    @Override
    public void intake(double speed) {
        roller.intake(speed);
    }
}
