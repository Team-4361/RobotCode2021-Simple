package frc.robot.subsystems.swerve.motor;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * An implementation of the {@code SwerveMotor} interface, designed specifically
 * (and only, actually) for motors controlled via the SPARK motor controller.
 *
 * @author Colin Robertson
 * @since 0.0.0
 */
public class SwerveSparkMotor implements SwerveMotor {
    /**
     * The motor's type.
     *
     * <p>
     * By default, all swerve drivetrain motors are brushless.
     * </p>
     */
    private static final MotorType TYPE = MotorType.kBrushless;

    /**
     * The motor's idle mode.
     *
     * <p>
     * We normally want the motor to brake when not powered.
     * </p>
     */
    private static final IdleMode IDLE = IdleMode.kBrake;

    /**
     * The minimum power the motor can receive that will still allow it to
     * turn forwards.
     */
    public static final double DEAD = 0.08;

    /**
     * Is the motor running in inverted mode?
     */
    private final boolean inverted;

    /**
     * A reference to the motor component itself.
     */
    private final CANSparkMax spark;

    /**
     * Create a new {@code SwerveSparkMotor} instance.
     *
     * @param id the motor's CAN ID.
     * @param inverted is the motor inverted?
     */
    public SwerveSparkMotor(int id,
                            boolean inverted) {
        spark = new CANSparkMax(id, TYPE);
        spark.setIdleMode(IDLE);

        this.inverted = inverted;
    }

    /**
     * Get the internally-used {@code CANSparkMax}.
     *
     * @return the internally-used {@code CANSparkMax}.
     */
    public CANSparkMax getSpark() {
        return spark;
    }

    /**
     * Set power to the motor.
     *
     * @param power the power value to set to the motor.
     */
    @Override
    public void setPower(double power) {
        if (inverted) spark.set(-power);
        else spark.set(power);
    }

    /**
     * Get the motor's power.
     *
     * @return the motor's power.
     */
    @Override
    public double getPower() {
        double power = spark.get();

        if (Math.abs(power) < DEAD) return 0;
        return spark.get();
    }
}
