package frc.robot.swerve.motor;

import frc.robot.swerve.encoder.SwerveCANEncoder;
import frc.robot.swerve.encoder.SwerveEncoder;

/**
 * A combination of the {@link SwerveMotor} and {@link SwerveEncoder}
 * interfaces - both a motor and an encoder.
 *
 * @author Colin Robertson
 * @since 0.0.0
 */
public class SwerveCombo implements SwerveMotor, SwerveEncoder {
    /**
     * SPARK MAX TYPE. DEFAULT.
     */
    public static final int SPARK = 0;

    /**
     * TALON TYPE. DEPRECATED
     *
     * @deprecated Use the {@link SwerveCombo#SPARK} instead.
     */
    public static final int TALON = 1;

    private SwerveMotor motor;
    private SwerveEncoder encoder;

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
        if (type == 0) {
            motor = new SwerveSparkMotor(
                    motorId,
                    isInverted
            );
            encoder = SwerveCANEncoder.get(
                    ((SwerveSparkMotor) motor).getSpark(),
                    cpr
            );
        } else if (type == 1) {
            throw new UnsupportedOperationException("No talons yet!");
        } else {
            throw new IllegalArgumentException("Invalid motor type!");
        }
    }

    @Override
    public double getPos() {
        return encoder.getPos();
    }

    @Override
    public int getCpr() {
        return encoder.getCpr();
    }

    @Override
    public void setPower(double power) {
        motor.setPower(power);
    }

    @Override
    public double getPower() {
        return motor.getPower();
    }
}
