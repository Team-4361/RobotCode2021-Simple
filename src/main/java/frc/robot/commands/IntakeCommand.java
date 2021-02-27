package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCommand extends Command {
    private static final OI.Buttons A = OI.Buttons.A;
    private static final OI.Buttons B = OI.Buttons.B;
    private static final OI.Buttons X = OI.Buttons.X;
    private static final OI.Buttons Y = OI.Buttons.Y;

    public IntakeCommand() {
        requires(IntakeSubsystem.getInstance());
    }

    private void actuatorControl() {
        boolean aPressed = Robot.getOi().getPressed(A);
        boolean bPressed = Robot.getOi().getPressed(B);

        if (aPressed) IntakeSubsystem.getInstance().actuateUp();
        if (bPressed) IntakeSubsystem.getInstance().actuateDown();
    }

    private void rollerControl() {
        double L = Robot.getOi().getTrigger(OI.Hands.L);
        double R = Robot.getOi().getTrigger(OI.Hands.R);

        double power = R - L;

        IntakeSubsystem.getInstance().intake(power);
    }

    @Override
    protected void execute() {
        actuatorControl();
        rollerControl();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
