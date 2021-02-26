package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.Robot;
import me.wobblyyyy.intra.ftc2.utils.math.Comparator;

/**
 * Command used for driving the robot.
 *
 * @author Colin Robertson
 * @since 0.0.0
 * @see DriveSubsystem
 */
public class DriveCommand extends Command {
    /**
     * Comparator for checking FORWARDS values.
     */
    private static final Comparator c_f = new Comparator(0.1);

    /**
     * Comparator for checking STRAFE values.
     */
    private static final Comparator c_s = new Comparator(0.1);

    /**
     * Comparator for checking TURN values.
     */
    private static final Comparator c_t = new Comparator(0.1);

    /**
     * Minimum FORWARDS value.
     */
    private static final double d_f = 0.1;

    /**
     * Minimum STRAFE value.
     */
    private static final double d_s = 0.1;

    /**
     * Minimum TURN value.
     */
    private static final double d_t = 0.1;

    /**
     * Forwards axis.
     *
     * <p>
     * Joystick: 2
     * </p>
     */
    public static final int FORWARDS_AXIS = 1;

    /**
     * Strafe axis.
     *
     * <p>
     * Joystick: 2
     * </p>
     */
    public static final int STRAFE_AXIS = 0;

    /**
     * Turn axis.
     *
     * <p>
     * Joystick: 1
     * </p>
     */
    public static final int TURN_AXIS = 0;

    /**
     * Forwards multiplier.
     */
    public static final int FORWARDS_MULTIPLIER = 1;

    /**
     * Strafe multiplier.
     */
    public static final int STRAFE_MULTIPLIER = 1;

    /**
     * Turn multiplier.
     */
    public static final int TURN_MULTIPLIER = 1;

    public DriveCommand() {
        requires(DriveSubsystem.getInstance());
    }

    /**
     * Drive the robot using controller input.
     *
     * <p>
     * This was designed incredibly poorly and very stupidly - whoever is
     * behind the subsystem and command interface did an absolutely awful
     * job of allowing for... y'know, normally functioning code?
     * </p>
     *
     * <p>
     * I have absolutely no clue why so much of this has to be static - it
     * would be a much better idea to pass objects as a parameters, thus
     * ensuring the debugging points of contact are easy to trace.
     * </p>
     */
    @Override
    protected void execute() {
        double F = Robot
                .getOi()
                .getSecondaryJoystick()
                .getRawAxis(FORWARDS_AXIS) * FORWARDS_MULTIPLIER;
        double S = Robot
                .getOi()
                .getSecondaryJoystick()
                .getRawAxis(STRAFE_AXIS) * STRAFE_MULTIPLIER;
        double T = Robot
                .getOi()
                .getPrimaryJoystick()
                .getRawAxis(TURN_AXIS) * TURN_MULTIPLIER;

        F = Math.abs(F) <= d_f ? 0 : F;
        S = Math.abs(S) <= d_s ? 0 : S;
        T = Math.abs(T) <= d_t ? 0 : T;

        Translation2d translation = new Translation2d(F, S);
        Rotation2d rotation = new Rotation2d(T);

        DriveSubsystem.getInstance().drive(
                translation,
                rotation
        );
    }

    /**
     * Has the command finished yet? Of course it hasn't. Our work is never
     * done in the world of robot drivetrains.
     *
     * @return false. Every time.
     */
    @Override
    protected boolean isFinished() {
        return false;
    }
}
