package frc.robot.subsystems.swerve.module;

/**
 * A resource class representing the state of a swerve module.
 *
 * @author Colin Robertson
 * @since 0.0.0
 */
public class ModuleState {
    private final double turn;
    private final double drive;

    /**
     * Create a new SwerveModuleState.
     *
     * @param turn  turn power (mps)
     * @param drive drive angle (radians)
     */
    public ModuleState(final double turn,
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
