package frc.robot;

public class RobotMap {
    public static final int DRIVETRAIN_FRONT_LEFT_ANGLE_MOTOR = 3; // CAN
    public static final int DRIVETRAIN_FRONT_LEFT_ANGLE_ENCODER = 3; // Analog
    public static final int DRIVETRAIN_FRONT_LEFT_DRIVE_MOTOR = 4; // CAN

    public static final int DRIVETRAIN_FRONT_RIGHT_ANGLE_MOTOR = 10; // CAN
    public static final int DRIVETRAIN_FRONT_RIGHT_ANGLE_ENCODER = 0; // Analog
    public static final int DRIVETRAIN_FRONT_RIGHT_DRIVE_MOTOR = 11; // CAN

    public static final int DRIVETRAIN_BACK_LEFT_ANGLE_MOTOR = 5; // CAN
    public static final int DRIVETRAIN_BACK_LEFT_ANGLE_ENCODER = 5; // Analog
    public static final int DRIVETRAIN_BACK_LEFT_DRIVE_MOTOR = 6; // CAN

    public static final int DRIVETRAIN_BACK_RIGHT_ANGLE_MOTOR = 8; // CAN
    public static final int DRIVETRAIN_BACK_RIGHT_ANGLE_ENCODER = 8; // Analog
    public static final int DRIVETRAIN_BACK_RIGHT_DRIVE_MOTOR = 9; // CAN

    public static final int ROLLERS = 0;      // talon
    public static final int ACTUATORS = 0;    // talon
    public static final int TOP_LIMIT = 0;    // digital input
    public static final int BOTTOM_LIMIT = 0; // digital input
    public static final int FLYWHEEL = 0;     // talon

    /*
     * Front-right.
     */
    public static final int FR_TURN = 10;
    public static final int FR_DRIVE = 11;

    /*
     * Front-left.
     */
    public static final int FL_TURN = 3;
    public static final int FL_DRIVE = 4;

    /*
     * Back-right.
     */
    public static final int BR_TURN = 8;
    public static final int BR_DRIVE = 9;

    /*
     * Back-left.
     */
    public static final int BL_TURN = 5;
    public static final int BL_DRIVE = 6;

    /*
     * Intake stuff! Or something...
     */
    public static final int INTAKE_TOP = 1;      // Digital input
    public static final int INTAKE_BOTTOM = 0;   // Digital input
    public static final int INTAKE_ACTUATOR = 1; // TALON over CAN
    public static final int INTAKE_ROLLER = 12;  // TALON over CAN
}
