package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.IntakeCommand;
import frc.robot.subsystems.intake.IntakeImpl;

public class IntakeSubsystem extends Subsystem implements Intake {
    private static IntakeSubsystem instance;
    private final Intake intake;

    public IntakeSubsystem(Intake intake) {
        this.intake = intake;
    }

    public static IntakeSubsystem getInstance() {
        if (instance == null) instance = new IntakeSubsystem(
                new IntakeImpl(
                        RobotMap.INTAKE_ACTUATOR,
                        RobotMap.INTAKE_ROLLER,
                        RobotMap.INTAKE_TOP,
                        RobotMap.INTAKE_BOTTOM
                )
        );

        return instance;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new IntakeCommand());
    }

    @Override
    public void actuateUp() {
        intake.actuateUp();
    }

    @Override
    public void actuateDown() {
        intake.actuateDown();
    }

    @Override
    public void intake(double speed) {
        intake.intake(speed);
    }
}
