package frc.robot;

public class RobotMap {
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

    /*
     * Shooter stuff! Or something... maybe...
     */
    public static final int SHOOTER_FLYWHEEL = 7; // SPARK over CAN

    /*
     * Storage stuff! Or something... yay!
     */
    public static final int STORAGE_INDEXER = 2; // TALON over CAN
}
