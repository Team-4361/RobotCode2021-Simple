package frc.robot.subsystems.shooter;

import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.Constants;

public class ShooterFlywheel {
    private final ShooterMotors motors;
    private final ShooterEncoders encoders;
    private final PIDController flywheelPid;
    private double power = 0;
    private double lastPower = 0;
    private boolean hasThreadStarted = false;
    private final Thread shooterThread;

    public ShooterFlywheel(ShooterMotors motors,
                           ShooterEncoders encoders) {
        this.motors = motors;
        this.encoders = encoders;

        this.flywheelPid = new PIDController(
                Constants.SHOOTER_KP,
                Constants.SHOOTER_KI,
                Constants.SHOOTER_KD
        );

        this.shooterThread = new Thread(() -> {
            do {
                double suggested = getShooterPower(power);

                motors.setFlywheelSpeed(suggested);
            } while (hasThreadStarted);
        });
    }

    /**
     * Calculate the suggested power for the shooter based on the input
     * value and the target velocity.
     *
     * @param power input power.
     * @return output power, goes to the motor.
     */
    private double getShooterPower(double power) {
        if (power != lastPower) {
            flywheelPid.reset();
        }
        lastPower = power;

        flywheelPid.setSetpoint(power * Constants.SHOOTER_TARGET);

        if (power == 0) return 0.0;

        double current = encoders.getFlywheelVelocity();

        double suggested = flywheelPid.calculate(current);

        if (suggested > 0) suggested *= 0.1;

        return suggested;
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
    public void shoot(double power) {
        // if (!hasThreadStarted) {
            // shooterThread.start();
            // hasThreadStarted = true;
        // }

        // this.power = power;
        motors.setFlywheelSpeed(power * -1.0);
    }
}
