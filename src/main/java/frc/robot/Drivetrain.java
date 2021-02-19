package frc.robot;

import org.roxbotix.elibs2.robot.components.LfComponent;

/**
 * A swerve drivetrain class. This class reflects the changes to the codebase
 * that have been made as of 2/18/2021 - this class is designed to be simple
 * and effective, and not much else.
 *
 * @author Colin Robertson
 */
public class Drivetrain implements LfComponent {
    private final SwerveModuleV2 fr =
            SwerveModuleFactory.getSwerveModule(SwerveModuleFactory.Module.FR);
    private final SwerveModuleV2 fl =
            SwerveModuleFactory.getSwerveModule(SwerveModuleFactory.Module.FL);
    private final SwerveModuleV2 br =
            SwerveModuleFactory.getSwerveModule(SwerveModuleFactory.Module.BR);
    private final SwerveModuleV2 bl =
            SwerveModuleFactory.getSwerveModule(SwerveModuleFactory.Module.BL);

    private final SwerveV2 swerve = new SwerveV2(
            fr, fl,
            br, bl,
            32, 32
    );

    /**
     * Initialize the drivetrain and all of its components.
     *
     * <p>
     * If we don't initialize the drivetrain, the drivetrain's motors might
     * not be as usable as we'd like them to be.
     * </p>
     */
    @Override
    public void init() {
        swerve.init();
    }

    public void drive(double leftStickX,
                      double leftStickY,
                      double rightStickX,
                      double rightStickY) {
        swerve.drive(
                leftStickX,
                leftStickY,
                rightStickX,
                rightStickY
        );
    }
}
