package frc.robot.subsystems.shooter;

import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.Constants;

public class ShooterFlywheel {
    private final ShooterMotors motors;
    private final ShooterEncoders encoders;
    private final PIDController flywheelPid;

    public ShooterFlywheel(ShooterMotors motors,
                           ShooterEncoders encoders) {
        this.motors = motors;
        this.encoders = encoders;

        this.flywheelPid = new PIDController(
                Constants.SHOOTER_KP,
                Constants.SHOOTER_KI,
                Constants.SHOOTER_KD
        );
        flywheelPid.setSetpoint(Constants.SHOOTER_TARGET);
    }

    /**
     * Make the shooter... well, shoot something.
     *
     * <p>
     * This method should be called whenever the shooter should be active.
     * The idea behind this method is to increase the accuracy of the shooter
     * by ensuring it stays as close as possible to a given RPM target, which
     * is defined in the "constants" class.
     * </p>
     *
     * <p>
     * If the shooter's determined power is above zero, meaning it should
     * spin in a positive direction, we only use a quarter of the suggested
     * power. It's easier to undershoot and correct for it than to overshoot
     * and not be able to correct very well.
     * </p>
     */
    public void shoot() {
        double currentVelocity = encoders.getFlywheelVelocity();
        double suggested = flywheelPid.calculate(currentVelocity);

        if (suggested > 0) suggested *= 0.25;

        motors.setFlywheelSpeed(suggested);
    }
}
