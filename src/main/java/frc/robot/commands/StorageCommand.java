package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.StorageSubsystem;

public class StorageCommand extends Command {
    public StorageCommand() {
        requires(StorageSubsystem.getInstance());
    }

    private void powerStorage() {
        double power = Robot.getOi().getTrigger(OI.Hands.R);

        StorageSubsystem.getInstance().move(power);
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
