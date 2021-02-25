package frc.robot.swerve.motor;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class SwerveSparkMotor implements SwerveMotor {
    private static final MotorType TYPE = MotorType.kBrushless;
    private final CANSparkMax spark;

    public SwerveSparkMotor(int id,
                            boolean inverted) {
        spark = new CANSparkMax(id, TYPE);
        spark.setInverted(inverted);
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
        return spark.get();
    }
}
