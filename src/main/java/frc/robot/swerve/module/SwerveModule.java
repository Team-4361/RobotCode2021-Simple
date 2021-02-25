package frc.robot.swerve.module;

import frc.robot.swerve.motor.SwerveCombo;
import frc.robot.swerve.motor.SwerveMotor;
import frc.robot.swerve.motor.SwerveSparkMotor;

/**
 * Simple swerve module.
 *
 * <p>
 * This module is NOT encoded. At least the drive motor isn't encoded. The
 * angle motor is always encoded.
 * </p>
 *
 * <p>
 * Swerve modules are made up of two components:
 * <ul>
 *     <li>
 *         {@link SwerveCombo} A combination motor and encoder.
 *     </li>
 *     <li>
 *         {@link SwerveMotor} A single swerve motor.
 *     </li>
 * </ul>
 * </p>
 *
 * @author Colin Robertson
 * @since 0.0.0
 */
public class SwerveModule {
    private static final int TURN_TYPE = 0;
    private static final int DRIVE_TYPE = 0;

    private SwerveCombo turn;
    private SwerveMotor drive;

    private final double origin;

    public SwerveModule(int turnId,
                        int driveId,
                        int cpr,
                        boolean isTurnInverted,
                        boolean isDriveInverted) {
        turn = new SwerveCombo(
                TURN_TYPE,
                turnId,
                cpr,
                isTurnInverted
        );
        drive = new SwerveSparkMotor(
                driveId,
                isDriveInverted
        );

        origin = turn.getPos();
    }

    public void setState(SwerveModuleState state) {
        turn.setPower(state.getTurn());
        drive.setPower(state.getDrive());
    }

    public double getAngle() {
        return turn.getPos();
    }

    public double getOffset() {
        return turn.getPos() - origin;
    }
}
