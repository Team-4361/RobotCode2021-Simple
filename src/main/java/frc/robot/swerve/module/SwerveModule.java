package frc.robot.swerve.module;

import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.swerve.motor.SwerveCombo;
import frc.robot.swerve.motor.SwerveMotor;
import frc.robot.swerve.motor.SwerveSparkMotor;
import me.wobblyyyy.intra.ftc2.utils.math.Math;

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

    private static final double KP = 0.55;
    private static final double KI = 0.0;
    private static final double KD = 0.005;

    private final PIDController turnController;

    protected static final double CLIP_MIN_TEST = -0.2;
    protected static final double CLIP_MAX_TEST = 0.2;
    protected static final double CLIP_MIN = -0.9;
    protected static final double CLIP_MAX = 0.9;

    private SwerveCombo turn;
    private SwerveMotor drive;

    private final int turnId;
    private final int driveId;
    private final int cpr;
    private final boolean isTurnInverted;
    private final boolean isDriveInverted;

    private final double origin;
    private final double angleOffset;

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
        angleOffset = getRealAngle();

        turnController = new PIDController(KP, KI, KD);

        this.turnId = turnId;
        this.driveId = driveId;
        this.cpr = cpr;
        this.isTurnInverted = isTurnInverted;
        this.isDriveInverted = isDriveInverted;
    }

    private double calculatePower(double angle) {
        return Math.clip(CLIP_MIN, CLIP_MAX, turnController.calculate(
            Math.toRadians(getAngle()),
            angle
        ));
    }

    public void setState(SwerveModuleState state) {
        turn.setPower(calculatePower(state.getTurn()));
        drive.setPower(state.getDrive());
    }

    private double getRealAngle() {
        double asAngle = turn.getPos() * 360;

        while (asAngle >= 360) asAngle -= 360;
        while (asAngle < -360) asAngle += 360;

        return asAngle;
    }

    public double getAngle() {
        double angle = getRealAngle();

        while (angle >= 360) angle -= 360;
        while (angle < -360) angle += 360;

        return getRealAngle() - angleOffset;
    }

    public double getOffset() {
        return turn.getPos() - origin;
    }

    public double getTurnVelocity() {
        return turn.getVelocity();
    }

    public SwerveCombo getTurn() {
        return turn;
    }

    public SwerveMotor getTurnMotor() {
        return turn.getMotor();
    }

    public SwerveMotor getDriveMotor() {
        return drive;
    }

    public int getTurnId() {
        return turnId;
    }

    public int getDriveId() {
        return driveId;
    }

    public int getCpr() {
        return cpr;
    }

    public boolean isTurnInverted() {
        return isTurnInverted;
    }

    public boolean isDriveInverted() {
        return isDriveInverted;
    }
}
