package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.StorageSubsystem;

public class StorageCommand extends Command {
    private static double POWER = 0.5;
    private static double ZERO = 0.0;

    public StorageCommand() {
        requires(StorageSubsystem.getInstance());
    }

    private void powerStorage() {
        if (Robot.getIo().getStorage()) {
            StorageSubsystem.getInstance().move(POWER);
        } else {
            StorageSubsystem.getInstance().move(ZERO);
        }
    }

    @Override
    protected void execute() {
        powerStorage();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
