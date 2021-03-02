package frc.robot.subsystems.swerve.module;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.subsystems.swerve.motor.SwerveCombo;
import frc.robot.subsystems.swerve.motor.SwerveMotor;
import me.wobblyyyy.intra.ftc2.utils.math.Math;

/**
 * Generic dual-encoded swerve module.
 *
 * <p>
 * Unlike the more simple {@link SwerveModule}, this type of swerve module
 * provides users with the ability to track the rotation, velocity, and
 * position of the module's drive wheel.
 * </p>
 *
 * <p>
 * Functionally, this is no different from the {@link SwerveModule} class.
 * In essence, it's just an extension of it.
 * </p>
 *
 * @author Colin Robertson
 * @see SwerveModule
 * @since 0.0.0
 */
public class SwerveEncodedModule {
    /**
     * The turn motor's type.
     *
     * <p>
     * Motor types can more accurately be described as encoder types. I don't
     * want to refactor this much code this late into the season.
     * </p>
     *
     * <p>
     * ALTERNATE ENCODER TYPE, FOR TURN MOTORS.
     * </p>
     */
    private static final int TURN_TYPE = SwerveCombo.SPARK;

    /**
     * The drive motor's type.
     *
     * <p>
     * Motor types can more accurately be described as encoder types. I don't
     * want to refactor this much code this late into the season.
     * </p>
     *
     * <p>
     * NEO ENCODER TYPE, FOR DRIVE MOTORS.
     * </p>
     */
    private static final int DRIVE_TYPE = SwerveCombo.NEO
    ;

    /**
     * Proportional coefficient.
     *
     * @see Constants#SWERVE_KP
     */
    private static final double KP = Constants.SWERVE_KP;

    /**
     * Integral coefficient.
     *
     * @see Constants#SWERVE_KI
     */
    private static final double KI = Constants.SWERVE_KI;

    /**
     * Derivative coefficient.
     *
     * @see Constants#SWERVE_KD
     */
    private static final double KD = Constants.SWERVE_KD;

    /**
     * PID controller used in effectively turning the motors.
     */
    private final PIDController turnController;

    /**
     * Minimum value for turn motor power.
     *
     * <p>
     * This value is multiplied by -1, as it is negative.
     * </p>
     *
     * @see Constants#TURN_CLIP
     */
    protected static final double CLIP_MIN = Constants.TURN_CLIP * -1;

    /**
     * Maximum value for turn motor power.
     *
     * <p>
     * This value is multiplied by +1, as it is positive.
     * </p>
     *
     * @see Constants#TURN_CLIP
     */
    protected static final double CLIP_MAX = Constants.TURN_CLIP * 1;

    /**
     * Turn combo. All swerve angle motors must be encoded, and therefore
     * {@code SwerveCombo} instances.
     */
    private final SwerveCombo turn;

    /**
     * Drive motor. Drive motors don't have to be encoded.
     *
     * <p>
     * Unlike the {@link SwerveModule} class, drive motors ARE encoded here.
     * As a result, this does, in fact, need to be encoded.
     * </p>
     */
    private final SwerveCombo drive;

    /**
     * Turn motor ID.
     */
    private final int turnId;

    /**
     * Drive motor ID.
     */
    private final int driveId;

    /**
     * Turn motor CPR.
     */
    private final int turnCpr;

    /**
     * Drive motor CPR.
     */
    private final int driveCpr;

    /**
     * Is the turn motor inverted?
     */
    private final boolean isTurnInverted;

    /**
     * Is the drive motor inverted?
     */
    private final boolean isDriveInverted;

    /**
     * The original angle the module started at.
     */
    private final double origin;

    /**
     * The offset from the original angle.
     */
    private final double angleOffset;

    /**
     * Create a new swerve module.
     *
     * @param turnId          the turn motor's ID.
     * @param driveId         the drive motor's ID.
     * @param turnCpr         the turn motor's counts per rotation.
     * @param driveCpr        the drive motor's counts per rotation.
     * @param isTurnInverted  is the turn wheel inverted?
     * @param isDriveInverted is the drive wheel inverted?
     */
    public SwerveEncodedModule(int turnId,
                               int driveId,
                               int turnCpr,
                               int driveCpr,
                               boolean isTurnInverted,
                               boolean isDriveInverted) {
        turn = new SwerveCombo(
                TURN_TYPE,
                turnId,
                turnCpr,
                isTurnInverted
        );
        drive = new SwerveCombo(
                DRIVE_TYPE,
                driveId,
                driveCpr,
                isDriveInverted
        );

        origin = turn.getPos();
        angleOffset = getRealAngle();

        turnController = new PIDController(KP, KI, KD);
        turnController.enableContinuousInput(-1, 1);

        this.turnId = turnId;
        this.driveId = driveId;
        this.turnCpr = turnCpr;
        this.driveCpr = driveCpr;
        this.isTurnInverted = isTurnInverted;
        this.isDriveInverted = isDriveInverted;
    }

    /**
     * Calculate power based on an angle.
     *
     * @param angle input angle, usually measured in degrees.
     * @return output power.
     */
    private double calculatePower(double angle) {
        return Math.clip(CLIP_MIN, CLIP_MAX, turnController.calculate(
                Math.toRadians(getAngle()),
                angle
        ));
    }

    /**
     * Set the swerve module's state.
     *
     * @param state the state to set to the swerve module.
     */
    public void setState(ModuleState state) {
        turn.setPower(calculatePower(state.getTurn()));
        drive.setPower(state.getDrive());
    }

    /**
     * Set the swerve module's state.
     *
     * <p>
     * In addition to applying the state to the swerve module, this method
     * makes use of the optimization code provided in the SwerveModuleState
     * class to optimize the wheel's turning.
     * </p>
     *
     * <p>
     * If optimization doesn't work, it should be removed. It will always be
     * a better idea to have working code than to have very cool experimental
     * code.
     * </p>
     *
     * <p>
     * Swerve module optimization is handled with WPILIB's optimization code:
     * {@link SwerveModuleState#optimize(SwerveModuleState, Rotation2d)}.
     * </p>
     *
     * @param state the swerve module's state.
     */
    public void setState(SwerveModuleState state) {
        SwerveModuleState.optimize(state, new Rotation2d(getAngle()));

        setState(new ModuleState(
                state.angle.getRadians(),
                state.speedMetersPerSecond
        ));
    }

    /**
     * Get the "real" angle without any offset.
     *
     * @return the "real" angle without any offset.
     */
    private double getRealAngle() {
        double asAngle = turn.getPos() * 360;

        while (asAngle >= 360) asAngle -= 360;
        while (asAngle < -360) asAngle += 360;

        return asAngle;
    }

    /**
     * Get the angle, with an offset.
     *
     * @return the angle the turn wheel is facing, with an offset.
     * @see SwerveEncodedModule#getRealAngle()
     */
    public double getAngle() {
        double angle = getRealAngle();

        while (angle >= 360) angle -= 360;
        while (angle < -360) angle += 360;

        return getRealAngle() - angleOffset;
    }

    /**
     * Get the turn motor's offset.
     *
     * @return the turn motor's offset.
     */
    public double getOffset() {
        return turn.getPos() - origin;
    }

    /**
     * Get the turn motor's velocity.
     *
     * @return the turn motor's velocity.
     */
    public double getTurnVelocity() {
        return turn.getVelocity();
    }

    /**
     * Get the drive motor's velocity.
     *
     * @return the drive motor's velocity.
     */
    public double getDriveVelocity() {
        return drive.getVelocity();
    }

    /**
     * Get the turn swerve combo.
     *
     * @return the turn swerve combo.
     * @see SwerveEncodedModule#getTurnMotor()
     */
    public SwerveCombo getTurn() {
        return turn;
    }

    /**
     * Get the drive swerve combo.
     *
     * @return the drive swerve combo.
     * @see SwerveEncodedModule#getDriveMotor()
     */
    public SwerveCombo getDrive() {
        return drive;
    }

    /**
     * Get the turn motor.
     *
     * @return the turn motor.
     * @see SwerveEncodedModule#getTurn()
     */
    public SwerveMotor getTurnMotor() {
        return turn.getMotor();
    }

    /**
     * Get the drive motor.
     *
     * @return the drive motor.
     * @see SwerveEncodedModule#getDrive()
     */
    public SwerveMotor getDriveMotor() {
        return drive;
    }

    /**
     * Get the turn motor's ID.
     *
     * @return the turn motor's ID.
     */
    public int getTurnId() {
        return turnId;
    }

    /**
     * Get the drive motor's ID.
     *
     * @return the drive motor's ID.
     */
    public int getDriveId() {
        return driveId;
    }

    /**
     * Get the turn motor's CPR.
     *
     * @return the turn motor's CPR.
     */
    public int getTurnCpr() {
        return turnCpr;
    }

    /**
     * Get the drive motor's CPR.
     *
     * @return the drive motor's CPR.
     */
    public int getDriveCpr() {
        return driveCpr;
    }

    /**
     * Is the turn motor inverted?
     *
     * @return is the turn motor inverted?
     */
    public boolean isTurnInverted() {
        return isTurnInverted;
    }

    /**
     * Is the drive motor inverted?
     *
     * @return is the drive motor inverted?
     */
    public boolean isDriveInverted() {
        return isDriveInverted;
    }
}
