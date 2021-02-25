package frc.robot.swerve.module;

/**
 * A resource class representing the state of a swerve module.
 *
 * @author Colin Robertson
 * @since 0.0.0
 */
public class SwerveModuleState {
    private final double turn;
    private final double drive;

    /**
     * Create a new SwerveModuleState.
     *
     * @param turn  turn power.
     * @param drive drive power.
     */
    public SwerveModuleState(final double turn,
                             final double drive) {
        this.turn = turn;
        this.drive = drive;
    }

    public double getTurn() {
        return turn;
    }

    public double getDrive() {
        return drive;
    }
}
