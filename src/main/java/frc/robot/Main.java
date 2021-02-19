package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;
import org.roxbotix.elibs3.main.ModeManager;

/**
 * Core initialization code.
 *
 * <p>
 * This code **SHOULD NOT** be modified unless you really know what you're
 * doing. The purpose of this class is to consult the ModeManager class to
 * determine what OpMode should be started, and then start that OpMode.
 * </p>
 *
 * <p>
 * As of 2/5/2021, all of the code within this file is working perfectly fine.
 * Unless it breaks, you should leave this one alone. There's next to no chance
 * that any error you're getting was thrown from this class right here.
 * </p>
 *
 * @author Colin Robertson
 */
public final class Main {
    /**
     * Actually initialize the robot - exciting, I know!
     *
     * <p>
     * This goes right to the ModeManager::get method - if you're having an
     * issue with getting the robot started, you should go check that out.
     * </p>
     */
    public static void main(String... args) {
        RobotBase.startRobot(Robot::new);
    }
}
