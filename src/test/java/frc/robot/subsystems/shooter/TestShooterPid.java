package frc.robot.subsystems.shooter;

import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.Constants;
import org.junit.jupiter.api.Test;

import java.util.Timer;
import java.util.TimerTask;

public class TestShooterPid {
    private final PIDController pid = new PIDController(
            Constants.SHOOTER_KP,
            Constants.SHOOTER_KI,
            Constants.SHOOTER_KD
    );

    private double velocity = 0;
    private int itr = 10;

    private double getVelocity() {
        return velocity;
    }

    private final TimerTask velocityTask = new TimerTask() {
        @Override
        public void run() {
            velocity -= 100;
        }
    };

    private final TimerTask pidTask = new TimerTask() {
        @Override
        public void run() {
            double calculated = pid.calculate(velocity);

            if (itr == 10) {
                itr = 0;
                System.out.println("Velocity: " + velocity);
                System.out.println("Calculated: " + calculated);
                System.out.println("");
            } else itr++;
        }
    };

    @Test
    public void testPid() throws InterruptedException {
        pid.setSetpoint(Constants.SHOOTER_TARGET);

        Timer timer = new Timer();
        timer.schedule(velocityTask, 0, 4);
        timer.schedule(pidTask, 0, 2);

        Thread.sleep(1000);
    }
}
