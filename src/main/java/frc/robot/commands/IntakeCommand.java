package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.OI.Hands;
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
        else if (bPressed) IntakeSubsystem.getInstance().actuateDown();
    }

    private void rollerControl() {
        double power = 0;

        boolean O = Robot.getOi().getBumper(Hands.L);
        boolean I = Robot.getOi().getBumper(Hands.R);
    
        if (O) power = -1.0;
        if (I) power = 1.0;
        
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
