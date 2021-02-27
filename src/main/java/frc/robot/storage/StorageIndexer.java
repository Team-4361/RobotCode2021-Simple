package frc.robot.storage;

public class StorageIndexer {
    private final StorageMotors motors;

    public StorageIndexer(StorageMotors motors) {
        this.motors = motors;
    }

    public void move(double speed) {
        motors.setIndexerSpeed(speed);
    }
}
