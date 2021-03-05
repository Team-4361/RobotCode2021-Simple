package frc.robot.auton;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import frc.robot.subsystems.DriveSubsystem;

public class DriveTenFeet extends TimedCommand {
    private boolean isFinished = false;
    private boolean stillDriving = true;

    public DriveTenFeet() {
        super(2000);
        requires(DriveSubsystem.getInstance());
    }

    @Override
    protected void execute() {
        // try {
            // (new Thread(() -> {
                // try {
                    // Thread.sleep(2000);
                    // stillDriving = false;
                // } catch (Exception e) {
                    // e.printStackTrace();
                // }
            // })).start();

            // while (stillDriving) {
                DriveSubsystem.getInstance().drive(
                    new Translation2d(
                        0.4,
                        0.0
                    ), 
                    new Rotation2d(0)
                );
            // }
        // } catch (Exception e) {
            // e.printStackTrace();
        // }
    }

    @Override
    protected void end() {
        DriveSubsystem.getInstance().drive(
                    new Translation2d(
                        0.4,
                        0.0
                    ), 
                    new Rotation2d(0)
                );
        isFinished = true;
    }

    @Override
    protected boolean isFinished() {
        return isFinished;
    }
}
