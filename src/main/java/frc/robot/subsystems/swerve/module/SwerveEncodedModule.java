package frc.robot.subsystems.swerve.module;

import frc.robot.subsystems.swerve.motor.SwerveCombo;

/**
 * Encoded swerve module.
 *
 * @author Colin Robertson
 * @since 0.0.0
 */
public class SwerveEncodedModule extends SwerveModule {
    private final SwerveCombo driveCombo;
    private final double wheelDiameter;
    private final double distanceConversion;
    private double lastPos = 0;

    /**
     * Create a new encoded swerve module.
     *
     * @param turnId          the module's turn motor CAN ID.
     * @param driveId         the module's drive motor CAN ID.
     * @param cpr             the module's drive motor CPR.
     * @param wheelDiameter   the module's drive wheel's diameter.
     * @param isTurnInverted  is the turn wheel inverted?
     * @param isDriveInverted is the drive wheel inverted?
     */
    public SwerveEncodedModule(int turnId,
                               int driveId,
                               int cpr,
                               double wheelDiameter,
                               boolean isTurnInverted,
                               boolean isDriveInverted) {
        super(turnId, driveId, cpr, isTurnInverted, isDriveInverted);

        driveCombo = new SwerveCombo(
                super.getDriveMotor(),
                SwerveCombo.NEO,
                4096,
                isDriveInverted
        );

        this.wheelDiameter = wheelDiameter;
        this.distanceConversion = wheelDiameter / 2.0;
    }

    public double getWheelDiameter() {
        return wheelDiameter;
    }

    public double getDistanceTravelled() {
        double distanceRads = getDrive().getPos() - lastPos;
        lastPos = getDrive().getPos();
        return distanceRads * distanceConversion;
    }

    public double getDriveVelocity() {
        return driveCombo.getVelocity();
    }

    public SwerveCombo getDrive() {
        return driveCombo;
    }
}
