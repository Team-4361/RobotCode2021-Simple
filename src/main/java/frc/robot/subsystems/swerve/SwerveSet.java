package frc.robot.subsystems.swerve;

import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import me.wobblyyyy.intra.ftc2.utils.math.Math;

/**
 * Utilities used in manipulating a set of swerve module states.
 *
 * @author Colin Robertson
 * @since 0.0.0
 */
public class SwerveSet {
    /**
     * Normalize a series of swerve drive powers based on current motor
     * velocities and combined errors.
     *
     * @param input an input array of swerve module powers.
     * @return an output array of normalized swerve module powers.
     */
    public static double[] getNormalizedPower(double[] input) {
        double[] normalized = new double[input.length];
        double min = Math.abs(input[0]);

        for (double d : input) {
            min = Math.min(min, Math.abs(d));
        }

        // if (min < 100) return new double[] {1.0, 1.0, 1.0, 1.0};
        return new double[] {1.0, 1.0, 1.0, 1.0};

        // int ctr = 0;
        // for (double d : input) {
            // normalized[ctr] = min / Math.abs(d);
            // ctr++;
        // }

        // return normalized;
    }

    /**
     * Normalize a set of {@code SwerveModuleState} instances based on array
     * of recorded velocity values. As swerve modules should typically always
     * run at the same speed, all the time, these values can be scaled at the
     * same time, thus allowing us to accurately tune a swerve chassis that
     * might otherwise be having some drift issues.
     *
     * <p>
     * As of 2021, the use case for this method is as follows.
     * </p>
     *
     * <p>
     * We have a chassis that works fine, with one minor issue - the back
     * left motor turns slower than all the rest of the motors. Rather than
     * trying to solve the problem mechanically, which never goes well, I
     * decided it would be a good idea to attempt to normalize the speeds of
     * these swerve modules based on previously recorded velocities to
     * (hopefully) counteract this problem. This solution is great because it
     * still allows the mechanical team to fix the issues they're having,
     * while allowing me to get a working chassis sooner.
     * </p>
     *
     * @param states     an array of {@code SwerveModuleState} instances. These
     *                   instances should be outputted by some form of
     *                   chassis speed to swerve module state code, often
     *                   handled by WPILIB.
     * @param velocities an array of recorded velocity values. These values
     *                   should be reported by drive motor encoders and should
     *                   be updated as frequently as possible.
     * @return a normalized set of swerve module states.
     */
    public static SwerveModuleState[] normalize(SwerveModuleState[] states,
                                                double[] velocities) {
        SwerveModuleState[] normalized = new SwerveModuleState[states.length];
        double[] normalizedVelocities = getNormalizedPower(velocities);

        int ctr = 0;
        for (SwerveModuleState state : states) {
            normalized[ctr] = new SwerveModuleState(
                    state.speedMetersPerSecond * normalizedVelocities[ctr],
                    state.angle
            );
            ctr++;
        }

        return states;
    }

    /**
     * Normalize a set of {@code SwerveModuleState} instances. Unlike the
     * {@link SwerveSet#getNormalizedPower(double[])} method, this method
     * normalizes an entire array of {code SwerveModuleState} instances,
     * rather than an array of raw doubles.
     *
     * @param input an array of {@code SwerveModuleState} instances that should
     *              be normalized.
     * @return a normalized array of swerve module states.
     * @deprecated Not entirely working.
     */
    @Deprecated
    public static SwerveModuleState[] normalizeStates(
            SwerveModuleState[] input) {
        int ctr;
        double[] powers = new double[input.length];
        SwerveModuleState[] normalized = new SwerveModuleState[input.length];

        ctr = 0;
        for (SwerveModuleState state : input) {
            powers[ctr] = state.speedMetersPerSecond;
            ctr++;
        }

        powers = getNormalizedPower(powers);

        ctr = 0;
        for (SwerveModuleState state : input) {
            normalized[ctr] = new SwerveModuleState(
                    powers[ctr],
                    state.angle
            );
        }

        return normalized;
    }
}
