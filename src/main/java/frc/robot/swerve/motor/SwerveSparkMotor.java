package frc.robot.swerve.motor;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class SwerveSparkMotor implements SwerveMotor {
    private static final MotorType TYPE = MotorType.kBrushless;
    private static final IdleMode IDLE = IdleMode.kBrake;
    public static final double DEAD = 0.08;
    private final CANSparkMax spark;

    public SwerveSparkMotor(int id,
                            boolean inverted) {
        spark = new CANSparkMax(id, TYPE);
        spark.setInverted(inverted);
        spark.setIdleMode(IDLE);
    }

    public CANSparkMax getSpark() {
        return spark;
    }

    @Override
    public void setPower(double power) {
        spark.set(power);
    }

    @Override
    public double getPower() {
        double power = spark.get();

        if (Math.abs(power) < DEAD) return 0;
        return spark.get();
    }
}
