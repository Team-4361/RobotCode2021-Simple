package frc.robot.storage;

public class Storage implements frc.robot.subsystems.Storage {
    private final StorageMotors motors;
    private final StorageIndexer indexer;

    public Storage(int flywheelId) {
        this.motors = new StorageMotors(flywheelId);

        indexer = new StorageIndexer(motors);
    }

    @Override
    public void move(double power) {
        indexer.move(power);
    }
}
