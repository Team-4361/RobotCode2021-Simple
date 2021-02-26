package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import frc.robot.commands.DriveCommand;
import frc.robot.swerve.chassis.SwerveChassis;

/**
 * Subsystem used for driving the robot.
 *
 * <p>
 * This isn't actually an extension of the subsystem class, as the amount
 * of static initialization required for a multi-subsystem robot is... well,
 * a little bit insane. It should function exactly identically, however.
 * </p>
 *
 * @author Colin Robertson
 * @see SwerveChassis
 * @since 0.0.0
 */
public class DriveSubsystem extends Subsystem implements Drive {
    private static DriveSubsystem driveSubsystem;
    private final Drive drive;

    /**
     * Create a new drive subsystem, using the provided drive interface.
     *
     * @param drive the drivetrain to use.
     */
    public DriveSubsystem(Drive drive) {
        this.drive = drive;
    }

    /**
     * Drive the robot.
     *
     * @param translation the drivetrain's translation. Translations are pairs
     *                    of X and Y values indicating how far in each
     *                    direction the vehicle should move.
     * @param rotation    the drivetrain's desired rotation. This rotation value
     *                    should normally be measured in radians per second,
     *                    but some of the math here is a bit funky.
     */
    @Override
    public void drive(Translation2d translation,
                      Rotation2d rotation) {
        drive.drive(
                translation,
                rotation
        );
    }

    /**
     * Get the current instance of the drivetrain.
     *
     * <p>
     * If no instance is declared (indicated by the drive subsystem field's
     * contents being null) we initialize a new drive subsystem by creating
     * a new one using a swerve chassis.
     * </p>
     *
     * <p>
     * TODO: Ideally, this should be handled with class reflection.
     * It's a problematic and stupid design to hard-code constants like this,
     * and any re-usable code should always be as configurable as possible.
     * </p>
     *
     * @return
     */
    public static DriveSubsystem getInstance() {
        if (driveSubsystem == null) driveSubsystem = new DriveSubsystem(
                new SwerveChassis()
        );

        return driveSubsystem;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DriveCommand());
    }
}
