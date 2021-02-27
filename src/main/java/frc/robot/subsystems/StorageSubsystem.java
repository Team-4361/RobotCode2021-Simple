package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.StorageCommand;

public class StorageSubsystem extends Subsystem implements Storage {
    private static StorageSubsystem instance;
    private final Storage storage;

    public StorageSubsystem(Storage storage) {
        this.storage = storage;
    }

    public static StorageSubsystem getInstance() {
        if (instance == null) instance = new StorageSubsystem(
                new frc.robot.subsystems.storage.Storage(
                        RobotMap.STORAGE_INDEXER
                )
        );

        return instance;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new StorageCommand());
    }

    @Override
    public void move(double power) {
        storage.move(power);
    }
}
