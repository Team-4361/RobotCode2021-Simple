package frc.robot;

/**
 * Constants used in multiple places throughout the codebase.
 *
 * <p>
 * The intention of this file is to allow even those who are unfamiliar with
 * the project to modify these values to a point which they're usable.
 * </p>
 *
 * <p>
 * Values shouldn't be changed except when they must be. This applies
 * especially to any of the proportional-integral-derivative coefficients
 * that are declared here - these take a while to tune, and unless you're
 * super confident you know what you're doing, it would be best to just leave
 * them alone.
 * </p>
 *
 * @author Colin Robertson
 * @since 0.0.0
 */
public class Constants {
    /**
     * Encoder CPR - used for drive wheels and turn wheels.
     */
    public static final double ENCODER_CPR = 4096;

    /**
     * Maximum distance from 0 for angle PID.
     */
    public static final double TURN_CLIP = 0.9;

    /**
     * Maximum distance from 0 for drive wheel velocity.
     */
    public static final double DRIVE_CLIP = 1.0;

    /**
     * Maximum distance from 0 for storage output power.
     */
    public static final double STORAGE_CLIP = 0.4;

    /**
     * Maximum distance from 0 for intake output power.
     */
    public static final double INTAKE_CLIP = 1.0;

    /**
     * The shooter's target velocity.
     *
     * <p>
     * This can be modified to however fast the shooter should should. Values
     * further away from zero (in the negative direction) make the shooter
     * shoot faster, and values closer to zero make the shooter shoot more
     * slowly. The shooter's maximum velocity is about 4000 rpm, so anything
     * past that will make the shooter shoot as fast as it possibly can.
     * </p>
     */
    public static final double SHOOTER_TARGET = -3500;

    public static final double SWERVE_KP = 0.550;
    public static final double SWERVE_KI = 0.000;
    public static final double SWERVE_KD = 0.005;

    public static final double SHOOTER_KP = 0.00032;
    public static final double SHOOTER_KI = 0.00000;
    public static final double SHOOTER_KD = 0.00005;
}
