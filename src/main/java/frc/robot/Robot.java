package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.robot.subsystems.DriveSubsystem;

public class Robot extends TimedRobot {
    private static OI oi;
    private static DriveSubsystem drive;

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
