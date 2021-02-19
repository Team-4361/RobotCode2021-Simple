package org.roxbotix.elibs2.motor;

import org.roxbotix.elibs2.robot.components.EncoderCore;
import org.roxbotix.elibs2.robot.components.LfComponent;
import org.roxbotix.elibs2.robot.components.MotorComponent;

import java.util.ArrayList;

/**
 * A stack of motors - motors that operate at the same power.
 *
 * @author Colin Robertson
 */
public class MotorStack implements
        MotorComponent,
        MotorCore,
        LfComponent,
        EncoderCore {
    private final ArrayList<Motor> motors = new ArrayList<>();

    /**
     * Create a new MotorStack.
     *
     * @param configs all of the different motor configurations.
     */
    public MotorStack(MotorConfig... configs) {
        for (MotorConfig config : configs) {
            motors.add(
                    new Motor(
                            config
                    )
            );
        }
    }

    /**
     * Internally-used method - set power.
     *
     * @param power new power for the motor.
     */
    private void _set(double power) {
        for (Motor motor : motors) {
            motor.set(power);
        }
    }

    /**
     * Internally-used method - get power.
     *
     * @return get the motor's power.
     */
    private double _get() {
        double total = 0;
        double count = motors.size();
        for (Motor motor : motors) {
            total += motor.get();
        }
        return total / count;
    }

    /**
     * Set offsets to all of the encoders.
     *
     * @param offset the offset to use.
     */
    private void setOffsets(int offset) {
        for (Motor motor : motors) {
            motor.setCountOffset(offset);
        }
    }

    /**
     * Get all of the offset averages.
     *
     * @return average offset.
     */
    private int getOffsets() {
        double total = 0;
        double count = motors.size();
        for (Motor motor : motors) {
            total += motor.getCountOffset();
        }
        return (int) (total / count);
    }

    /**
     * Get average count.
     *
     * @return average encoder count.
     */
    private int getAvgCount() {
        double total = 0;
        double count = motors.size();
        for (Motor motor : motors) {
            total += motor.getCount();
        }
        return (int) (total / count);
    }

    /**
     * Zero all of the encoder counts.
     */
    private void _zero() {
        for (Motor motor : motors) {
            motor.zeroCount();
        }
    }

    /**
     * Initialize all of the motors.
     */
    private void initMotors() {
        for (Motor motor : motors) {
            motor.init();
        }
    }

    /**
     * Set power to the motor.
     *
     * @param power a double, between the values of -1 (negative one) and +1
     *              (positive one). This double, when multiplied by 100, would
     *              represent the percentage of power (positive or negative)
     */
    @Override
    public void setPower(double power) {
        _set(power);
    }

    /**
     * Get the motor's power.
     *
     * @return the motor's power.
     */
    @Override
    public double getPower() {
        return _get();
    }

    /**
     * Get the encoder's count.
     *
     * @return the encoder's count.
     */
    @Override
    public int getCount() {
        return getAvgCount();
    }

    /**
     * Set the encoder's counting offset.
     *
     * <p>
     * Caleb wants me to put this here... "csgo"
     * </p>
     *
     * @param offset the encoder's count offset.
     */
    @Override
    public void setCountOffset(int offset) {
        setOffsets(offset);
    }

    /**
     * Get the current count offset.
     *
     * @return the encoder's counting offset.
     */
    @Override
    public int getCountOffset() {
        return getOffsets();
    }

    /**
     * Zero the encoder count.
     */
    @Override
    public void zeroCount() {
        _zero();
    }

    /**
     * Initialize the motor stack.
     */
    @Override
    public void init() {
        initMotors();
    }

    /**
     * Set power.
     *
     * @param power a double, with a range of +1 to -1 (positive one to negative
     *              one), that represents the amount of power the motor should
     *              receive. A power of 1 is 100% of the output in the positive
     *              direction. A power of -0.75 is 75% of the output in the
     */
    @Override
    public void set(double power) {
        _set(power);
    }

    /**
     * Get power.
     *
     * @return the motor's power.
     */
    @Override
    public double get() {
        return _get();
    }
}
