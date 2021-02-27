package frc.robot.swerve.encoder;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

/**
 * CAN encoder based off a Spark motor controller.
 *
 * @author Colin Robertson
 * @since 0.0.0
 */
public class SwerveCANEncoder implements SwerveEncoder {
    private final CANEncoder encoder;
    private final int cpr;

    public SwerveCANEncoder(CANSparkMax spark,
                            int cpr) {
        encoder = spark.getAlternateEncoder(cpr);
        this.cpr = cpr;
    }

    public static SwerveCANEncoder get(CANSparkMax spark,
                                       int cpr) {
        return new SwerveCANEncoder(spark, cpr);
    }

    @Override
    public double getPos() {
        return encoder.getPosition();
    }

    @Override
    public double getVelocity() {
        return encoder.getVelocity();
    }

    @Override
    public int getCpr() {
        return cpr;
    }
}
