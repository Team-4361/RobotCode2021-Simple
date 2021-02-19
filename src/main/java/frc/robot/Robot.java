// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * As of 2/18/2021, a simplified version of the robot's codebase is to be
 * kept in a separate repository.
 *
 * <p>
 * This simplified code should utilize only a single package (frc.robot) and
 * not depend on any JNIs or additional libraries that aren't included in
 * wpilib's Maven repository.
 * </p>
 *
 * <p>
 * Multi-threading here might still be a good idea, however. Re-hashing an
 * entire codebase to operate on a single thread would be incredibly and
 * painstakingly challenging. Should we extend the {@code ThreadedController}
 * class provided by elibs2 to make use of multi-threading?
 * </p>
 *
 * <p>
 * Motor construction and initialization should be handled by the init
 * phase of the robot's operation. Drive control should be available in
 * TeleOp, by making use of the controller inputs the drive station should
 * have.
 * </p>
 *
 * @author Team 4361 - ROXBOTIX
 * @author Colin Robertson
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  Drivetrain drivetrain = new Drivetrain();
  Joystick _left = new Joystick(0);
  Joystick _right = new Joystick(0);
  Stick left;
  Stick right;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    drivetrain.init();
    left = new Stick(_left);
    right = new Stick(_right);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    double lsx = left.getX();
    double lsy = left.getY();
    double rsx = right.getX();
    double rsy = right.getY();

    drivetrain.drive(lsx, lsy, rsx, rsy);
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
