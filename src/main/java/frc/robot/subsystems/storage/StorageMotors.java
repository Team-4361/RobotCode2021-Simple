package frc.robot.subsystems.storage;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import frc.robot.Constants;
import me.wobblyyyy.intra.ftc2.utils.math.Math;

public class StorageMotors {
    private static final ControlMode MODE = ControlMode.PercentOutput;

    private static final double INDEXER_COEFFICIENT = -0.5;

    private final TalonSRX indexer;

    private double indexerSpeed = 0;

    public StorageMotors(int indexerId) {
        indexer = new TalonSRX(indexerId);
    }

    private double calculateIndexerSpeed(double input) {
        return Math.clip(
                Constants.STORAGE_CLIP * -1,
                Constants.STORAGE_CLIP * 1,
                input * INDEXER_COEFFICIENT
        );
    }

    public void setIndexerSpeed(double speed) {
        speed = calculateIndexerSpeed(speed);
        indexer.set(MODE, speed);
        this.indexerSpeed = speed;
    }

    public double getIndexerSpeed() {
        return indexerSpeed;
    }
}
