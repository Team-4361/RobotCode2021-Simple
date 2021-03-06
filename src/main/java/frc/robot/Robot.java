package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import frc.robot.auton.Autonomous;
import frc.robot.auton.DriveTenFeet;
import frc.robot.auton.PathfinderImpl;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.StorageSubsystem;
import me.wobblyyyy.edt.DynamicArray;
import me.wobblyyyy.pathfinder.api.Pathfinder;
import me.wobblyyyy.pathfinder.geometry.HeadingPoint;

/**
 * The main robot class for the 2021 season's simplified robot code.
 *
 * <p>
 * All of the elements in this class are fairly self-documenting. Parts of
 * this class that are confusing without documentation are documented. Before
 * asking about "well, how does this work?" it would be greatly appreciated if
 * you could read the documentation or read the code behind how it works.
 * </p>
 *
 * @author Colin Robertson
 * @since 0.0.0
 */
public class Robot extends TimedRobot {
    /**
     * Revamped user input class.
     */
    private static IO io;

    /**
     * Drive subsystem.
     */
    private static DriveSubsystem drive;

    /**
     * Intake subsystem.
     */
    private static IntakeSubsystem intake;

    /**
     * Shooter subsystem.
     */
    private static ShooterSubsystem shooter;

    /**
     * Storage subsystem.
     */
    private static StorageSubsystem storage;

    /**
     * Command for autonomous mode (???)
     */
    private static Command autonomousCommand;

    private PathfinderImpl pf;

    private Pathfinder pathfinder;

    /**
     * Get the robot's revamped input class.
     *
     * @return the robot's IO class.
     */
    public static IO getIo() {
        return io;
    }

    /**
     * Get the drive subsystem.
     *
     * @return the drive subsystem.
     */
    public static DriveSubsystem getDrive() {
        return drive;
    }

    /**
     * Called once on robot initialization.
     */
    @Override
    public void robotInit() {
        io = new IO();

        //autonomousCommand = new DriveTenFeet();

        drive = DriveSubsystem.getInstance();
        intake = IntakeSubsystem.getInstance();
        shooter = ShooterSubsystem.getInstance();
        storage = StorageSubsystem.getInstance();

        //pf = new PathfinderImpl(drive.getSwerveChassis());
        //pathfinder = pf.getPathfinder();
    }

    @Override
    public void teleopInit() {
        // pathfinder.close();
    }

    private final DriveCommand driveCommand = new DriveCommand();

    @Override
    public void teleopPeriodic() {
        // pathfinder.close();
        drive.getSwerveChassis().enableUserControl();
        Scheduler.getInstance().run();
        // driveCommand.execute();
    }

    /**
     * Called every so often whenever the robot is active.
     */
    @Override
    public void robotPeriodic() {
    }

    /**
     * Called every so often whenever the robot is in test mode.
     */
    @Override
    public void testPeriodic() {
        // Scheduler.getInstance().run();
        // pathfinder.close();
    }

    @Override
    public void autonomousInit() {
        pf = new PathfinderImpl(drive.getSwerveChassis());
        drive.getSwerveChassis().disableUserControl();
        // Uncomment the Autonomous path you want to run
        // all valid paths are in the Autonomous class
        // they're each a static dynamic array
        Autonomous.execute(pf, Autonomous.barrelPath);
        // Autonomous.execute(pf, Autonomous.slalomPath);
        // Autonomous.execute(pf, Autonomous.barrelPath);
        // Autonomous.execute(pf, Autonomous.lightspeedCircuitPath);
        // Autonomous.execute(pf, Autonomous.rectangleTest);
        // Autonomous.execute(pf, Autonomous.barrelPath);
        //Autonomous.execute(pf, Autonomous.barrelRacingPath);
        //Autonomous.execute(pf, Autonomous.bouncePath);
        drive.getSwerveChassis().enableUserControl();
        drive.getSwerveChassis().stopAllMotors();
    }
}
