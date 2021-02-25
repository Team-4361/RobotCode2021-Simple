package frc.robot.swerve.chassis;

import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.subsystems.Drive;
import frc.robot.swerve.encoder.SwerveCANEncoder;
import frc.robot.swerve.module.SwerveModule;
import frc.robot.swerve.motor.SwerveCombo;
import frc.robot.swerve.motor.SwerveSparkMotor;

/**
 * Representation of a swerve drivetrain.
 *
 * @author Colin Robertson
 * @see Drive
 * @see SwerveCANEncoder
 * @see SwerveSparkMotor
 * @see SwerveCombo
 * @since 0.0.0
 */
public class SwerveChassis implements Drive {
    /**
     * Meters-to-inches conversion factor.
     */
    private static final double MTI = 0.0254;

    /**
     * Inches-to-meters conversion factor.
     */
    private static final double ITM = 39.3701;

    /**
     * Normal / not inverted flag.
     */
    private static final boolean NORMAL = false;

    /**
     * Abnormal / inverted flag.
     */
    private static final boolean INVERTED = true;

    /**
     * CPR for all turn motors.
     */
    private static final int CPR = 4096;

    /**
     * Horizontal wheelbase.
     */
    private static final double WHEELBASE_X = 23.5;

    /**
     * Vertical wheelbase.
     */
    private static final double WHEELBASE_Y = 23.5;

    /**
     * Should the front-right module be inverted?
     */
    private static final boolean FR_INVERT = NORMAL;

    /**
     * Should the front-left module be inverted?
     */
    private static final boolean FL_INVERT = NORMAL;

    /**
     * Should the back-right module be inverted?
     */
    private static final boolean BR_INVERT = NORMAL;

    /**
     * Should the back-left module be inverted?
     */
    private static final boolean BL_INVERT = NORMAL;

    /**
     * The front-right module's offset, in degrees.
     */
    private static final double FR_OFFSET_DEG = 0;

    /**
     * The front-left module's offset, in degrees.
     */
    private static final double FL_OFFSET_DEG = 0;

    /**
     * The back-right module's offset, in degrees.
     */
    private static final double BR_OFFSET_DEG = 0;

    /**
     * The back-left module's offset, in degrees.
     */
    private static final double BL_OFFSET_DEG = 0;

    /**
     * Front-right offset in radians.
     *
     * <p>
     * This value is calculated automatically - just don't touch it.
     * </p>
     */
    private static final double FR_OFFSET_RAD = Math.toRadians(FR_OFFSET_DEG);

    /**
     * Front-left offset in radians.
     *
     * <p>
     * This value is calculated automatically - just don't touch it.
     * </p>
     */
    private static final double FL_OFFSET_RAD = Math.toRadians(FL_OFFSET_DEG);

    /**
     * Back-right offset in radians.
     *
     * <p>
     * This value is calculated automatically - just don't touch it.
     * </p>
     */
    private static final double BR_OFFSET_RAD = Math.toRadians(BR_OFFSET_DEG);

    /**
     * Back-left offset in radians.
     *
     * <p>
     * This value is calculated automatically - just don't touch it.
     * </p>
     */
    private static final double BL_OFFSET_RAD = Math.toRadians(BL_OFFSET_DEG);

    /**
     * Horizontal wheelbase, in meters.
     *
     * <p>
     * This value is calculated automatically - just don't touch it.
     * </p>
     */
    private static final double WHEELBASE_X_METERS = WHEELBASE_X * MTI;

    /**
     * Vertical wheelbase, in meters.
     *
     * <p>
     * This value is calculated automatically - just don't touch it.
     * </p>
     */
    private static final double WHEELBASE_Y_METERS = WHEELBASE_Y * MTI;

    /**
     * Rotational coefficient based off the hypotenuse of a hypothetical
     * triangle composed of legs A and B, where A and B are the horizontal
     * and vertical wheelbases of the robot.
     */
    private static final double R = Math.hypot(
            WHEELBASE_X_METERS,
            WHEELBASE_Y_METERS
    );

    /**
     * Kinematics used in determining swerve module states.
     *
     * <p>
     * These are provided by WPILIB and the math here SHOULD work. This can,
     * for the most part, be left entirely alone.
     * </p>
     */
    private final SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
            new Translation2d(WHEELBASE_X_METERS / 2.0, WHEELBASE_Y_METERS / 2.0),
            new Translation2d(WHEELBASE_X_METERS / 2.0, -WHEELBASE_Y_METERS / 2.0),
            new Translation2d(-WHEELBASE_X_METERS / 2.0, WHEELBASE_Y_METERS / 2.0),
            new Translation2d(-WHEELBASE_X_METERS / 2.0, -WHEELBASE_Y_METERS / 2.0)
    );

    /**
     * The front-right swerve module.
     */
    private final SwerveModule frModule;

    /**
     * The front-left swerve module.
     */
    private final SwerveModule flModule;

    /**
     * The back-right swerve module.
     */
    private final SwerveModule brModule;

    /**
     * The back-left swerve module.
     */
    private final SwerveModule blModule;

    /**
     * Create a new swerve chassis.
     *
     * <p>
     * This constructor doesn't accept any parameters - rather, it makes use
     * of the static fields that are initialized prior to this class's
     * instantiation.
     * </p>
     */
    public SwerveChassis() {
        frModule = new SwerveModule(
                RobotMap.FR_TURN,
                RobotMap.FR_DRIVE,
                CPR,
                FR_INVERT,
                NORMAL
        );
        flModule = new SwerveModule(
                RobotMap.FL_TURN,
                RobotMap.FL_DRIVE,
                CPR,
                FL_INVERT,
                NORMAL
        );
        brModule = new SwerveModule(
                RobotMap.BR_TURN,
                RobotMap.BR_DRIVE,
                CPR,
                BR_INVERT,
                NORMAL
        );
        blModule = new SwerveModule(
                RobotMap.BL_TURN,
                RobotMap.BL_DRIVE,
                CPR,
                BL_INVERT,
                NORMAL
        );
    }

    /**
     * Get a power value from two angles.
     *
     * <p>
     * As for now, this method uses the following ruleset:
     * <ul>
     *     <li>
     *         If the target is 90 degrees away from the current angle, we
     *         tell the motor to go at full speed.
     *     </li>
     *     <li>
     *         If the target is less than 90 degrees, the motor will go as
     *         fast as close to completion it is.
     *     </li>
     * </ul>
     * </p>
     *
     * @param current module current angle.
     * @param target  module target angle.
     * @return a power value.
     */
    private double getPowerFromAngle(double current,
                                     double target) {
        current = Math.toDegrees(current);
        target = Math.toDegrees(target);

        if (target >= current + 90) {
            return 1;
        }

        if (target <= current - 90) {
            return -1;
        }

        double calculated = (target - current) / 90;
        return Math.abs(calculated) < 0.08 ? 0 : calculated;
    }

    /**
     * Update the shuffleboard display to show the following information:
     *
     * <p>
     * <ul>
     *     <li>
     *         Front-right angle offset.
     *     </li>
     *     <li>
     *         Front-left angle offset.
     *     </li>
     *     <li>
     *         Back-right angle offset.
     *     </li>
     *     <li>
     *         Back-left angle offset.
     *     </li>
     * </ul>
     * </p>
     */
    private void updateDisplay() {
        SmartDashboard.putNumber("fro", frModule.getOffset());
        SmartDashboard.putNumber("flo", flModule.getOffset());
        SmartDashboard.putNumber("bro", brModule.getOffset());
        SmartDashboard.putNumber("blo", blModule.getOffset());
    }

    /**
     * Physically drive the drivetrain.
     *
     * @param translation the drivetrain's translation. Translations are pairs
     *                    of X and Y values indicating how far in each
     *                    direction the vehicle should move.
     * @param rotation    the drivetrain's desired rotation. This rotation value
     *                    should normally be measured in radians per second,
     *                    but some of the math here is a bit funky.
     */
    @Override
    public void drive(Translation2d translation, Rotation2d rotation) {
        updateDisplay();

        ChassisSpeeds speeds = new ChassisSpeeds(
                translation.getX(),
                translation.getY(),
                rotation.getRadians() * 2 / R
        );

        SwerveModuleState[] states = kinematics.toSwerveModuleStates(speeds);

        frModule.setState(new frc.robot.swerve.module.SwerveModuleState(
                getPowerFromAngle(
                        frModule.getAngle(),
                        states[0].angle.getRadians()
                ),
                states[0].speedMetersPerSecond
        ));
        flModule.setState(new frc.robot.swerve.module.SwerveModuleState(
                getPowerFromAngle(
                        flModule.getAngle(),
                        states[1].angle.getRadians()
                ),
                states[1].speedMetersPerSecond
        ));
        brModule.setState(new frc.robot.swerve.module.SwerveModuleState(
                getPowerFromAngle(
                        brModule.getAngle(),
                        states[2].angle.getRadians()
                ),
                states[2].speedMetersPerSecond
        ));
        blModule.setState(new frc.robot.swerve.module.SwerveModuleState(
                getPowerFromAngle(
                        blModule.getAngle(),
                        states[3].angle.getRadians()
                ),
                states[3].speedMetersPerSecond
        ));
    }
}
