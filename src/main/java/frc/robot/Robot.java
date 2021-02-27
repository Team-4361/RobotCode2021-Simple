package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.StorageSubsystem;

public class Robot extends TimedRobot {
    private static OI oi;
    private static DriveSubsystem drive;
    private static IntakeSubsystem intake;
    private static ShooterSubsystem shooter;
    private static StorageSubsystem storage;

    public static OI getOi() {
        return oi;
    }

    public static DriveSubsystem getDrive() {
        return drive;
    }

    @Override
    public void robotInit() {
        oi = new OI();
        drive = DriveSubsystem.getInstance();
        intake = IntakeSubsystem.getInstance();
        shooter = ShooterSubsystem.getInstance();
        storage = StorageSubsystem.getInstance();
    }

    @Override
    public void robotPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void testPeriodic() {
        Scheduler.getInstance().run();
    }
}
