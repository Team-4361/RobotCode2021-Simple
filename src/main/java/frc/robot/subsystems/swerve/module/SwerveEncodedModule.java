package frc.robot.subsystems.swerve.module;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
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
 * <p>
 * This is the suggested alternative to the {@link SwerveModule} class because
 * the {@code SwerveModule} class has outdated math. If you'd like to fix the
 * math in the {@code SwerveModule} class you can feel free to do so, but
 * otherwise it's suggested that you just use this one.
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
     * <p>
     * The obvious method is proportional control: the motor current is set in
     * proportion to the existing error. However, this method fails if, for
     * instance, the arm has to lift different weights: a greater weight
     * needs a greater force applied for the same error on the down side,
     * but a smaller force if the error is on the upside. That's where the
     * integral and derivative terms play their part.
     * </p>
     *
     * <p>
     * Term P is proportional to the current value of the SP-PV error e(t). For
     * example, if the error is large and positive, the control output will be
     * proportionately large and positive, taking into account the gain factor
     * "K". Using proportional control alone will result in an error between
     * the setpoint and the actual process value because it requires an error
     * to generate the proportional response. If there is no error, there
     * is no corrective response.
     * </p>
     *
     * <p>
     * A high proportional gain results in a large change in the output for a
     * given change in the error. If the proportional gain is too high, the
     * system can become unstable (see the section on loop tuning). In contrast,
     * a small gain results in a small output response to a large input error,
     * and a less responsive or less sensitive controller. If the proportional
     * gain is too low, the control action may be too small when responding to
     * system disturbances. Tuning theory and industrial practice indicate that
     * the proportional term should contribute the bulk of the output change.
     * </p>
     *
     * @see Constants#SWERVE_KP
     */
    private static final double KP = Constants.SWERVE_KP;

    /**
     * Integral coefficient.
     *
     * <p>
     * An integral term increases action in relation not only to the error but
     * also the time for which it has persisted. So, if the applied force is
     * not enough to bring the error to zero, this force will be increased as
     * time passes. A pure "I" controller could bring the error to zero, but
     * it would be both slow reacting at the start (because the action would be
     * small at the beginning, needing time to get significant) and brutal (the
     * action increases as long as the error is positive, even if the error has
     * started to approach zero).
     * </p>
     *
     * <p>
     * Term I accounts for past values of the SP-PV error and integrates them
     * over time to produce the I term. For example, if there is a residual
     * SP-PV error after the application of proportional control, the integral
     * term seeks to eliminate the residual error by adding a control effect
     * due to the historic cumulative value of the error. When the error is
     * eliminated, the integral term will cease to grow. This will result
     * in the proportional effect diminishing as the error decreases,
     * but this is compensated for by the growing integral effect.
     * </p>
     *
     * <p>
     * The integral term accelerates the movement of the process towards
     * setpoint and eliminates the residual steady-state error that occurs
     * with a pure proportional controller. However, since the integral term
     * responds to accumulated errors from the past, it can cause the present
     * value to overshoot the setpoint value (see the section on loop tuning).
     * </p>
     *
     * @see Constants#SWERVE_KI
     */
    private static final double KI = Constants.SWERVE_KI;

    /**
     * Derivative coefficient.
     *
     * <p>
     * A derivative term does not consider the error (meaning it cannot bring
     * it to zero: a pure D controller cannot bring the system to its setpoint),
     * but the rate of change of error, trying to bring this rate to zero. It
     * aims at flattening the error trajectory into a horizontal line, damping
     * the force applied, and so reduces overshoot (error on the other side
     * because of too great applied force). Applying too much impetus when the
     * error is small and decreasing will lead to overshoot. After overshooting,
     * if the controller were to apply a large correction in the opposite
     * direction and repeatedly overshoot the desired position, the output
     * would oscillate around the setpoint in either a constant, growing, or
     * decaying sinusoid. If the amplitude of the oscillations increases with
     * time, the system is unstable. If they decrease, the system is stable.
     * If the oscillations remain at a constant magnitude, the system is
     * considered marginally stable.
     * </p>
     *
     * <p>
     * Term D is a best estimate of the future trend of the SP-PV error, based
     * on its current rate of change. It is sometimes called "anticipatory
     * control", as it is effectively seeking to reduce the effect of the SP-PV
     * error by exerting a control influence generated by the rate of error
     * change. The more rapid the change, the greater the controlling or
     * damping effect.
     * </p>
     *
     * <p>
     * Derivative action predicts system behavior and thus improves settling
     * time and stability of the system. An ideal derivative is not causal, so
     * that implementations of PID controllers include an additional low-pass
     * filtering for the derivative term to limit the high-frequency gain and
     * noise. Derivative action is seldom used in practice though – by one
     * estimate in only 25% of deployed controllers – because of its variable
     * impact on system stability in real-world applications.
     * </p>
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
     * <p>
     * Upon creation, this swerve module will use a {@link PIDController}
     * defined with constants kP, kI, and kD, all of which are available
     * and documented above.
     * </p>
     *
     * <p>
     * These values, for the 2021 season at least, are defined in the
     * {@link Constants} class, not right here.
     * </p>
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
     * <p>
     * This method will take an input value from a swerve module state and
     * turn it into a power value by making use of the {@link PIDController}
     * declared earlier in this class.
     * </p>
     *
     * @param angle input angle, measured in radians.
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
        // SwerveModuleState optimized = SwerveModuleState
                // .optimize(state, new Rotation2d(getAngleRads()));
        SwerveModuleState optimized = SwerveModuleState.optimize(
            state,
            Rotation2d.fromDegrees(getAngle())
        );

        setState(new ModuleState(
                optimized.angle.getRadians(),
                optimized.speedMetersPerSecond
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
     * Get the swerve module's angle, in radians.
     *
     * <p>
     * This output value will always be within the range of (0, 2). It won't
     * ever be negative, because negative numbers are incredibly lame.
     * </p>
     *
     * @return the swerve module's angle, in radians.
     */
    public double getAngleRads() {
        return Math.toRadians(getAngle());
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
