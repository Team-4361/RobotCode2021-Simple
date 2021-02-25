package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.Robot;
import org.frcteam2910.common.robot.Utilities;

public class DriveCommand extends Command {
    // Forwards: secondary joystick
    // Strafe: secondary joystick
    // Turn: primary joystick
    public static final int FORWARDS_AXIS = 1;
    public static final int STRAFE_AXIS = 0;
    public static final int TURN_AXIS = 0;
    public static final int FORWARDS_MULTIPLIER = -1;
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

        SmartDashboard.putNumber("Forwards", forward);
        SmartDashboard.putNumber("Strafe", strafe);
        SmartDashboard.putNumber("Turn", turn);

        // double forward = -Robot.getOi().getPrimaryJoystick().getRawAxis(1);
        forward = Utilities.deadband(forward);
        // Square the forward stick
        forward = Math.copySign(Math.pow(forward, 2.0), forward);

        // double strafe = -Robot.getOi().getPrimaryJoystick().getRawAxis(0);
        strafe = Utilities.deadband(strafe);
        // Square the strafe stick
        strafe = Math.copySign(Math.pow(strafe, 2.0), strafe);

        // double rotation = -Robot.getOi().getSecondaryJoystick().getRawAxis(1);
        turn = Utilities.deadband(turn);
        // Square the rotation stick
        turn = Math.copySign(Math.pow(turn, 2.0), turn);

        DrivetrainSubsystem.getInstance().drive(new Translation2d(forward, strafe), turn, true);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
