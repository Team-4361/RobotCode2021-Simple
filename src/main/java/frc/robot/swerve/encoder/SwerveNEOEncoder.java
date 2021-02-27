package frc.robot.swerve.encoder;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.EncoderType;

/**
 * CAN encoder based off a Spark motor controller.
 *
 * @author Colin Robertson
 * @since 0.0.0
 */
public class SwerveNEOEncoder implements SwerveEncoder {
    private static final EncoderType TYPE = EncoderType.kQuadrature;
    private final CANEncoder encoder;
    private final int cpr;

    public SwerveNEOEncoder(CANSparkMax spark,
                            int cpr) {
        encoder = spark.getEncoder(TYPE, cpr);
        this.cpr = cpr;
    }

    public static SwerveNEOEncoder get(CANSparkMax spark,
                                       int cpr) {
        return new SwerveNEOEncoder(spark, cpr);
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
