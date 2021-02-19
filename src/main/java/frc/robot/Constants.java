package frc.robot;

public class Constants {
    public static int FR_DRIVE_ID = 0;
    public static int FL_DRIVE_ID = 0;
    public static int BR_DRIVE_ID = 0;
    public static int BL_DRIVE_ID = 0;

    public static int FR_TURN_ID = 0;
    public static int FL_TURN_ID = 0;
    public static int BR_TURN_ID = 0;
    public static int BL_TURN_ID = 0;

    public static int FR_DRIVE_ENCODER_A = 0;
    public static int FR_DRIVE_ENCODER_B = 0;
    public static int FL_DRIVE_ENCODER_A = 0;
    public static int FL_DRIVE_ENCODER_B = 0;
    public static int BR_DRIVE_ENCODER_A = 0;
    public static int BR_DRIVE_ENCODER_B = 0;
    public static int BL_DRIVE_ENCODER_A = 0;
    public static int BL_DRIVE_ENCODER_B = 0;

    public static int FR_TURN_ENCODER_A = 0;
    public static int FR_TURN_ENCODER_B = 0;
    public static int FL_TURN_ENCODER_A = 0;
    public static int FL_TURN_ENCODER_B = 0;
    public static int BR_TURN_ENCODER_A = 0;
    public static int BR_TURN_ENCODER_B = 0;
    public static int BL_TURN_ENCODER_A = 0;
    public static int BL_TURN_ENCODER_B = 0;

    public static int DRIVE_ENCODER_CPR = 1024;
    public static int TURN_ENCODER_CPR = 1024;
    public static double WHEEL_DIAMETER = 5;

    public static int[] get_ids_for_fr() {
        return new int[] {
                FR_DRIVE_ID,
                FR_TURN_ID,
                FR_DRIVE_ENCODER_A,
                FR_DRIVE_ENCODER_B,
                FR_TURN_ENCODER_A,
                FR_TURN_ENCODER_B
        };
    }

    public static int[] get_ids_for_fl() {
        return new int[] {
                FL_DRIVE_ID,
                FL_TURN_ID,
                FL_DRIVE_ENCODER_A,
                FL_DRIVE_ENCODER_B,
                FL_TURN_ENCODER_A,
                FL_TURN_ENCODER_B
        };
    }

    public static int[] get_ids_for_br() {
        return new int[] {
                BR_DRIVE_ID,
                BR_TURN_ID,
                BR_DRIVE_ENCODER_A,
                BR_DRIVE_ENCODER_B,
                BR_TURN_ENCODER_A,
                BR_TURN_ENCODER_B
        };
    }

    public static int[] get_ids_for_bl() {
        return new int[] {
                BL_DRIVE_ID,
                BL_TURN_ID,
                BL_DRIVE_ENCODER_A,
                BL_DRIVE_ENCODER_B,
                BL_TURN_ENCODER_A,
                BL_TURN_ENCODER_B
        };
    }
}
