package frc.robot.intake;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class IntakeRoller {
    private final TalonSRX roller;

    public IntakeRoller(IntakeMotors motors) {
        this.roller = motors.getRoller();
    }
}
