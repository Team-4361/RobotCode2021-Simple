package frc.robot.subsystems.swerve.motor;

import frc.robot.subsystems.swerve.encoder.SwerveCANEncoder;
import frc.robot.subsystems.swerve.encoder.SwerveEncoder;
import frc.robot.subsystems.swerve.encoder.SwerveNEOEncoder;

/**
 * A combination of the {@link SwerveMotor} and {@link SwerveEncoder}
 * interfaces - both a motor and an encoder.
 *
 * @author Colin Robertson
 * @since 0.0.0
 */
public class SwerveCombo implements SwerveMotor, SwerveEncoder {
    private static final String NO_SPARK = "Spark motors are not supported.";
    private static final String NO_NEO = "Neo motors are not supported.";
    private static final String NO_TALON = "Talon motors are not supported.";
    private static final String NO_SRX = "SRX motors are not supported.";
    private static final String WRONG_TYPE = "Invalid motor type!";

    /**
     * SPARK MAX TYPE. DEFAULT.
     */
    public static final int SPARK = 0;

    /**
     * NEO TYPE. PROBABLY USED FOR DRIVE MOTORS.
     */
    public static final int NEO = 1;

    /**
     * TALON TYPE.
     */
    public static final int TALON = 2;

    /**
     * Internally-used motor.
     */
    private final SwerveMotor motor;

    /**
     * Internally-used encoder.
     */
    private final SwerveEncoder encoder;

    /**
     * Create a new {@code SwerveCombo} instance.
     *
     * @param motor      the combo's swerve motor.
     * @param type       the swerve motor's type.
     * @param cpr        the {@code SwerveCombo}'s encoder's CPR.
     * @param isInverted should the motor be inverted?
     */
    public SwerveCombo(SwerveMotor motor,
                       int type,
                       int cpr,
                       boolean isInverted) {
        this.motor = motor;

        switch (type) {
            case 0:
                encoder = SwerveCANEncoder.get(((SwerveSparkMotor) motor)
                        .getSpark(), cpr);
                break;
            case 1:
                encoder = SwerveNEOEncoder.get(((SwerveSparkMotor) motor)
                        .getSpark(), cpr);
                break;
            case 2:
                throw new UnsupportedOperationException(NO_TALON);
            case 3:
                throw new UnsupportedOperationException(NO_SRX);
            default:
                throw new IllegalArgumentException(WRONG_TYPE);
        }
    }

    /**
     * Create a new {@code SwerveCombo} instance.
     *
     * @param type       the swerve module's type. At least for the 2021
     *                   season, this should always be either "0" or
     *                   {@link SwerveCombo#SPARK}
     * @param motorId    the motor's ID. Different motors handle ID codes
     *                   in different ways, but generally this is a CAN ID
     *                   that's defined in the {@link frc.robot.RobotMap}.
     * @param cpr        the angle encoder's counts per rotation. The most
     *                   common value here is {@code 4096}
     * @param isInverted is the drive motor inverted? Typically, motors on
     *                   the left side or the right side will be inverted -
     *                   just pick a side and invert the motors.
     */
    public SwerveCombo(int type,
                       int motorId,
                       int cpr,
                       boolean isInverted) {
        switch (type) {
            case 0:
                motor = new SwerveSparkMotor(motorId, isInverted);
                encoder = SwerveCANEncoder.get(((SwerveSparkMotor) motor)
                        .getSpark(), cpr);
                break;
            case 1:
                motor = new SwerveSparkMotor(motorId, isInverted);
                encoder = SwerveNEOEncoder.get(((SwerveSparkMotor) motor)
                        .getSpark(), cpr);
                break;
            case 2:
                throw new UnsupportedOperationException(NO_TALON);
            default:
                throw new IllegalArgumentException(WRONG_TYPE);
        }
    }

    /**
     * Get the combo's motor.
     *
     * @return the combo's motor.
     */
    public SwerveMotor getMotor() {
        return motor;
    }

    /**
     * Get the combo's position.
     *
     * <p>
     * This is a position value reported by the encoder. In most cases, this
     * value will be measured in radians, with a range of (-1, 1).
     * </p>
     *
     * @return the combo's position.
     */
    @Override
    public double getPos() {
        return encoder.getPos();
    }

    /**
     * Get the combo's encoder's velocity.
     *
     * @return the combo's encoder's velocity.
     */
    @Override
    public double getVelocity() {
        return encoder.getVelocity();
    }

    /**
     * Get the combo's encoder's counts per rotation.
     *
     * @return get the combo's encoder's counts per rotation.
     */
    @Override
    public int getCpr() {
        return encoder.getCpr();
    }

    /**
     * Set power to the combo's motor.
     *
     * @param power the power value to set to the combo's motor. This power
     *              value should most often be within a range of (-1, 1).
     */
    @Override
    public void setPower(double power) {
        motor.setPower(power);
    }

    /**
     * Get the combo's current power value.
     *
     * @return the combo's current power value.
     */
    @Override
    public double getPower() {
        return motor.getPower();
    }
}
