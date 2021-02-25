package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.Robot;
import org.frcteam2910.common.robot.Utilities;

public class DriveCommand extends Command {
    public static final int FORWARDS_AXIS = 1;
    public static final int STRAFE_AXIS = 0;
    public static final int TURN_AXIS = 0;
    public static final int FORWARDS_MULTIPLIER = 1;
    public static final int STRAFE_MULTIPLIER = 1;
    public static final int TURN_MULTIPLIER = 1;

    public DriveCommand() {
        requires(DrivetrainSubsystem.getInstance());
    }

    @Override
    protected void execute() {
        double forward = Robot
            .getOi()
            .getSecondaryJoystick()
            .getRawAxis(FORWARDS_AXIS);
        double strafe = Robot
            .getOi()
            .getSecondaryJoystick()
            .getRawAxis(STRAFE_AXIS);
        double turn = Robot
            .getOi()
            .getPrimaryJoystick()
            .getRawAxis(TURN_AXIS);

        forward = Utilities.deadband(forward);
        forward = Math.copySign(Math.pow(forward, 1.0), forward);

        strafe = Utilities.deadband(strafe);
        strafe = Math.copySign(Math.pow(strafe, 1.0), strafe);

        turn = Utilities.deadband(turn);
        turn = Math.copySign(Math.pow(turn, 1.0), turn);

        DrivetrainSubsystem.getInstance().drive(new Translation2d(forward, strafe), turn, true);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
