package frc.robot.subsystems.swerve.chassis;

import java.util.function.Supplier;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.swerve.SwerveSet;
import frc.robot.subsystems.swerve.encoder.SwerveCANEncoder;
import frc.robot.subsystems.swerve.module.SwerveEncodedModule;
import frc.robot.subsystems.swerve.motor.SwerveCombo;
import frc.robot.subsystems.swerve.motor.SwerveSparkMotor;
import me.wobblyyyy.edt.DynamicArray;
import me.wobblyyyy.edt.StaticArray;
import me.wobblyyyy.edt.functional.Analyzable;
import me.wobblyyyy.edt.functional.Normalizable;
import me.wobblyyyy.intra.ftc2.utils.math.Comparator;
import me.wobblyyyy.intra.ftc2.utils.math.Math;

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
    private final AHRS navx;

    /**
     * Comparator used in determining if the chassis's angle
     * is close enough to being correct.
     */
    private static final Comparator angleComparator = new Comparator(2);
    
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
    private static final boolean FL_INVERT = INVERTED;

    /**
     * Should the back-right module be inverted?
     */
    private static final boolean BR_INVERT = NORMAL;

    /**
     * Should the back-left module be inverted?
     */
    private static final boolean BL_INVERT = INVERTED;

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
     * 
     * <p>
     * The order of this is as follows:
     * <ul>
     * <li>Front-left</li>
     * <li>Front-right</li>
     * <li>Back-left</li>
     * <li>Back-right</li>
     * </ul>
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
    private final SwerveEncodedModule frModule;

    /**
     * The front-left swerve module.
     */
    private final SwerveEncodedModule flModule;

    /**
     * The back-right swerve module.
     */
    private final SwerveEncodedModule brModule;

    /**
     * The back-left swerve module.
     */
    private final SwerveEncodedModule blModule;

    private final Suppliers suppliers;
    private final StaticArray<Supplier<Double>> velocitySuppliers;
    private final StaticArray<Supplier<Double>> powerSuppliers;

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
        frModule = new SwerveEncodedModule(
                RobotMap.FR_TURN,
                RobotMap.FR_DRIVE,
                CPR,
                CPR,
                FR_INVERT,
                FR_INVERT
        );
        flModule = new SwerveEncodedModule(
                RobotMap.FL_TURN,
                RobotMap.FL_DRIVE,
                CPR,
                CPR,
                NORMAL,
                FL_INVERT
        );
        brModule = new SwerveEncodedModule(
                RobotMap.BR_TURN,
                RobotMap.BR_DRIVE,
                CPR,
                CPR,
                BR_INVERT,
                BR_INVERT
        );
        blModule = new SwerveEncodedModule(
                RobotMap.BL_TURN,
                RobotMap.BL_DRIVE,
                CPR,
                CPR,
                NORMAL,
                BL_INVERT
        );

        navx = new AHRS(SPI.Port.kMXP);

        suppliers = new Suppliers(
            frModule, 
            flModule, 
            brModule, 
            blModule
        );

        velocitySuppliers = suppliers.getVelocitySuppliers();
        powerSuppliers = suppliers.getPowerSuppliers();
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
        SmartDashboard.putNumber(
                "FR Velocity",
                frModule.getDriveVelocity()
        );
        SmartDashboard.putNumber(
                "FR Power",
                frModule.getDriveMotor().getPower()
        );

        SmartDashboard.putNumber(
                "FL Velocity",
                flModule.getDriveVelocity()
        );
        SmartDashboard.putNumber(
                "FL Power",
                flModule.getDriveMotor().getPower()
        );

        SmartDashboard.putNumber(
                "BR Velocity",
                brModule.getDriveVelocity()
        );
        SmartDashboard.putNumber(
                "BR Power",
                brModule.getDriveMotor().getPower()
        );

        SmartDashboard.putNumber(
                "BL Velocity",
                blModule.getDriveVelocity()
        );
        SmartDashboard.putNumber(
                "BL Power",
                blModule.getDriveMotor().getPower()
        );
        
        SmartDashboard.putNumber(
            "NavX Angle", 
            navx.getAngle()
        );
    }

    private ChassisSpeeds getSpeeds(Translation2d translation, Rotation2d rotation) {
        return ChassisSpeeds.fromFieldRelativeSpeeds(
                translation.getX(), 
                translation.getY(), 
                rotation.getRadians() * 2 / R, 
                Rotation2d.fromDegrees(-navx.getAngle())
        );
    }

    private SwerveModuleState[] getNormalStates(ChassisSpeeds speeds) {
        return kinematics.toSwerveModuleStates(speeds);
    }

    private SwerveModuleState[] getStates(ChassisSpeeds speeds) {
        SwerveModuleState[] originalStates = 
            kinematics.toSwerveModuleStates(speeds);

        suppliers.setStates(originalStates);

        Analyzable<Double> normalizedPowers = Normalizable.normalizeSuppliers(
            velocitySuppliers, 
            powerSuppliers
        );
        DynamicArray<Double> powers = new DynamicArray<>(normalizedPowers);
        // SmartDashboard.putNumberArray("powers", powers.toDoubleArray());

        double maxPower = Math.abs(normalizedPowers.maximum().doubleValue());
        double minPower = Math.abs(normalizedPowers.minimum().doubleValue());
        double max = maxPower < minPower ? minPower : maxPower;

        powers.itr().forEach(power -> {
            if (Math.abs(power) < (max / 2)) {
                final double pm = power < 0 ? -1.0 : 1.0;
                powers.set(powers.itr().index(), (max * pm * 1.00));
            }
        });

        SwerveModuleState[] newStates = new SwerveModuleState[] {
            new SwerveModuleState(
                powers.get(1) * 0.97,
                originalStates[0].angle
            ),
            new SwerveModuleState(
                powers.get(0) * 1.00,
                originalStates[1].angle
            ),
            new SwerveModuleState(
                powers.get(3) * 0.97,
                originalStates[2].angle
            ),
            new SwerveModuleState(
                powers.get(2) * 1.00,
                originalStates[3].angle
            ),
        };

        return newStates;
    }

    private void setStates(SwerveModuleState[] states) {
        frModule.setState(states[1]);
        flModule.setState(states[0]);
        brModule.setState(states[3]);
        blModule.setState(states[2]);
    }

    /**
     * Physically drive the drivetrain.
     *
     * <p>
     * In addition to setting power to the motors, we do some (very cool)
     * calculations behind the scene. The first of these is set normalization,
     * offered by the {@link SwerveSet#normalize(SwerveModuleState[], double[])}
     * method. This is designed to prevent robot drift by normalizing all
     * swerve motor powers based on recorded encoder velocities.
     * </p>
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
        // Rotation2d current = Rotation2d.fromDegrees(navx.getAngle());
        // Rotation2d difference = new Rotation2d(rotation.getRadians() - current.getRadians());
        // todo angle correction
        ChassisSpeeds speeds = getSpeeds(translation, rotation);
        // SwerveModuleState[] states = getNormalStates(speeds);
        SwerveModuleState[] states = getStates(speeds);
        setStates(states);
        updateDisplay();

        // updateDisplay();

        // ChassisSpeeds speeds = new ChassisSpeeds(
                // translation.getX(),
                // translation.getY(),
                // rotation.getRadians() * 2 / R
        // );

        // ChassisSpeeds speeds = getSpeeds(translation, rotation);

        // SwerveModuleState[] states = getStates(speeds);

        // setStates(states);

        // speeds = ChassisSpeeds.fromFieldRelativeSpeeds(translation.getX(), translation.getY(), rotation,
                //     Rotation2d.fromDegrees(gyroscope.getAngle().toDegrees()));

        // SwerveModuleState[] states = kinematics.toSwerveModuleStates(speeds);

        // states = SwerveSet.normalize(
                // states,
                // new double[] {
                        // flModule.getDriveVelocity(),
                        // frModule.getDriveVelocity(),
                        // blModule.getDriveVelocity(),
                        // brModule.getDriveVelocity()
                // }
        // );
    }
}
