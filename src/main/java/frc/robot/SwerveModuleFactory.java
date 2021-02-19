package frc.robot;

/**
 * Utility class - create and return swerve modules based on inputted
 * parameters. The reasoning behind the creation of this class has largely to
 * do with the obscene amount of code that would be required otherwise to
 * create and instantiate all four swerve modules that we need.
 *
 * @author Colin Robertson
 */
public class SwerveModuleFactory {
    public static SwerveModuleV2 getSwerveModule(Module module) {
        int[] ids;

        switch (module) {
            case FR:
                ids = Constants.get_ids_for_fr();
                break;
            case FL:
                ids = Constants.get_ids_for_fl();
                break;
            case BR:
                ids = Constants.get_ids_for_br();
                break;
            case BL:
                ids = Constants.get_ids_for_bl();
                break;
            default:
                ids = new int[10];
                break;
        }

        int drive_id = ids[0];
        int turn_id = ids[1];
        int drive_encoder_a = ids[2];
        int drive_encoder_b = ids[3];
        int turn_encoder_a = ids[4];
        int turn_encoder_b = ids[5];
        double wheelDiameter = Constants.WHEEL_DIAMETER;
        int drive_cpr = Constants.DRIVE_ENCODER_CPR;
        int turn_cpr = Constants.TURN_ENCODER_CPR;

        return new SwerveModuleV2(
                drive_id,
                turn_id,
                drive_encoder_a,
                drive_encoder_b,
                turn_encoder_a,
                turn_encoder_b,
                drive_cpr,
                turn_cpr,
                wheelDiameter
        );
    }

    public static Motor[] getDriveMotors() {
        return null;
    }

    public static Motor[] getTurnMotors() {
        return null;
    }

    public enum Module {
        FR,
        FL,
        BR,
        BL
    }
}
