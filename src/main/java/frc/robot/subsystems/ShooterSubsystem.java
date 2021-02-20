package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.ShooterCommand;

import static com.ctre.phoenix.motorcontrol.ControlMode.PercentOutput;

public class ShooterSubsystem extends Subsystem {
    private static final TalonSRX _flywheel = new TalonSRX(RobotMap.FLYWHEEL);

    private static final ShooterSubsystem instance = new ShooterSubsystem(
            _flywheel
    );

    /**
     * The motor used for controlling the flywheel.
     */
    private final TalonSRX flywheel;

//    /**
//     * The motor used for actuating the shooting mechanism.
//     */
//    private final TalonSRX actuator;

//    /**
//     * The encoder that keeps track of the flywheel's rate of spinning.
//     */
//    private final Encoder flywheelEncoder;

//    /**
//     * The encoder that keeps track of the actuator's position.
//     */
//    private final Encoder actuatorEncoder;

//    /**
//     * The encoder's TPR.
//     *
//     * <p>
//     * TPR = Ticks Per Rotation.
//     * </p>
//     */
//    private final double flywheelTpr;

//    /**
//     * The actuator's TPR.
//     *
//     * <p>
//     * TPR = Ticks Per Rotation.
//     * </p>
//     */
//    private final double actuatorTpr;

//    /**
//     * The flywheel's diameter.
//     */
//    private final double flywheelDiameter;

    /**
     * Create a new shooter.
     *
     * @param flywheel the flywheel motor.
     *                 //     * @param actuator         the actuator motor.
     *                 //     * @param flywheelEncoder  the flywheel's encoder.
     *                 //     * @param actuatorEncoder  the actuator's encoder.
     *                 //     * @param flywheelTpr      the flywheel's TPR value.
     *                 //     * @param actuatorTpr      the actuator's TPR value.
     *                 //     * @param flywheelDiameter the diameter of the flywheel.
     */
    public ShooterSubsystem(TalonSRX flywheel // ,
//                            TalonSRX actuator,
//                            Encoder flywheelEncoder,
//                            Encoder actuatorEncoder,
//                            double flywheelTpr,
//                            double actuatorTpr,
            /*                            double flywheelDiameter*/) {
        this.flywheel = flywheel;
//        this.actuator = actuator;
//        this.flywheelEncoder = flywheelEncoder;
//        this.actuatorEncoder = actuatorEncoder;
//        this.flywheelTpr = flywheelTpr;
//        this.actuatorTpr = actuatorTpr;
//        this.flywheelDiameter = flywheelDiameter;
    }

    /**
     * Get the fly wheel.
     *
     * @return the fly wheel.
     */
    public TalonSRX getFlywheel() {
        return flywheel;
    }

//    /**
//     * Get the flywheel's encoder.
//     *
//     * @return the flywheel's encoder.
//     */
//    public Encoder getFlywheelEncoder() {
//        return flywheelEncoder;
//    }

//    /**
//     * Get the actuator's encoder.
//     *
//     * @return the actuator's encoder.
//     */
//    public Encoder getActuatorEncoder() {
//        return actuatorEncoder;
//    }

//    /**
//     * Get the flywheel's TPR.
//     *
//     * @return the flywheel's TPR.
//     */
//    public double getFlywheelTpr() {
//        return flywheelTpr;
//    }

//    /**
//     * Get the actuator's TPR.
//     *
//     * @return the actuator's TPR.
//     */
//    public double getActuatorTpr() {
//        return actuatorTpr;
//    }

//    /**
//     * Get the diameter of the flywheel.
//     *
//     * @return the flywheel's diameter.
//     */
//    public double getFlywheelDiameter() {
//        return flywheelDiameter;
//    }

    /**
     * Start the shooter.
     *
     * @param speed how fast the motor controller should go. This is a value
     *              between -100 and 100 and represents a percentage of the
     *              controller's maximum speed.
     */
    public void shoot(double speed) {
        double convSpeed = speed / 100;
        flywheel.set(PercentOutput, convSpeed);
    }

    /**
     * Stop the shooter.
     */
    public void stopShooting() {
        shoot(0.0);
    }

//    /**
//     * Start the actuator.
//     *
//     * @param speed how fast the motor controller should go. This is a value
//     *              between -100 and 100 and represents a percentage of the
//     *              controller's maximum speed.
//     */
//    public void actuate(double speed) {
//        double convSpeed = speed / 100;
//        actuator.set(PercentOutput, convSpeed);
//    }

//    /**
//     * Stop the actuator.
//     */
//    public void stopActuating() {
//        actuate(0.0);
//    }

//    /**
//     * Get the velocity of the flywheel.
//     *
//     * <p><i>
//     * Note from Colin:
//     * Last I heard, as of the Saturday meeting on 1/23/2021, this code isn't
//     * guaranteed to work. This definitely should get tested and addressed
//     * whenever possible.
//     * </i></p>
//     *
//     * @return the velocity of the flywheel.
//     */
//    public double getVelocity() {
//        double tps = flywheelEncoder.getRate();
//        return (tps / flywheelTpr) * flywheelDiameter;
//    }

    public static ShooterSubsystem getInstance() {
        return instance;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ShooterCommand());
    }
}
