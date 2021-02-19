package org.roxbotix.elibs2.motor;

/**
 * The legendary configuration class for a motor! Woo-woo!
 *
 * <p>
 * Read the fields here if you're confused about what each of these options
 * does. I promise - they each have a very real impact on the motor's
 * performance.
 * </p>
 *
 * @author Colin Robertson
 */
public class MotorConfig {
    /**
     * Default multiplier.
     */
    public static double MULTIPLIER = 1.0;

    /**
     * Default pre-multiplier min.
     */
    public static double PRE_MULTIPLIER_MIN = -1000;

    /**
     * Default pre-multiplier max.
     */
    public static double PRE_MULTIPLIER_MAX = 1000;

    /**
     * Default minimum.
     */
    public static double MIN = -1.0;

    /**
     * Default maximum.
     */
    public static double MAX = 1.0;

    /**
     * Default deadzone.
     */
    public static double DEADZONE = 0.0;

    /**
     * Default direction.
     */
    public static Direction DIRECTION = Direction.FORWARDS;

    /**
     * Default encoder state.
     */
    public static boolean USES_ENCODER = false;

    /**
     * Default PID state.
     */
    public static boolean USES_PID = false;

    /**
     * Default PID center.
     */
    public static double PID_CENTER = 0;

    /**
     * Default PID CPR.
     */
    public static double PID_CPR = 0;

    /**
     * Default encoder IDs.
     *
     * <p>
     * Encoders with the ID "-1" are classified as disabled encoders.
     * </p>
     */
    public static int ENCODER_ID = -1;

    /**
     * The motor's hardware identification code.
     */
    public int id;

    /**
     * The motor's power value.
     *
     * <p>
     * Power is always represented as a value between -1 and +1, where -1
     * represents the maximum speed in the backwards direction and +1 represents
     * the maximum speed in the positive direction.
     * </p>
     */
    public double power;

    /**
     * The motor's multiplier value.
     *
     * <p>
     * All inputted power values are multiplied by this value before any
     * power is actually set to the motor.
     * </p>
     */
    public double multiplier;

    /**
     * The motor's POSITIVE multiplier value.
     *
     * <p>
     * All positive inputted power values are multiplied by this value before
     * any power is actually set to the motor.
     * </p>
     */
    public double multiplierPositive;

    /**
     * The motor's NEGATIVE multiplier value.
     *
     * <p>
     * All negative inputted power values are multiplied by this value before
     * any power is actually set to the motor.
     * </p>
     */
    public double multiplierNegative;

    /**
     * Pre-multiplier minimum power input.
     *
     * <p>
     * Anything below this value is rounded up to this value.
     * </p>
     */
    public double preMultiplierMin;

    /**
     * Pre-multiplier maximum power input.
     *
     * <p>
     * Anything above this value is rounded down to this value.
     * </p>
     */
    public double preMultiplierMax;

    /**
     * Post-multiplier minimum power input.
     *
     * <p>
     * Anything below this value is rounded up to this value.
     * </p>
     */
    public double min;

    /**
     * Post-multiplier maximum power input.
     *
     * <p>
     * Anything above this value is rounded up to this value.
     * </p>
     */
    public double max;

    /**
     * The motor's POSITIVE deadzone.
     *
     * ABSOLUTE VALUE!!
     *
     * <p>
     * Anything below this value (ABSOLUTE VALUE!!) is set to 0.
     * </p>
     */
    public double deadzonePositive;

    /**
     * The motor's NEGATIVE deadzone.
     *
     * ABSOLUTE VALUE!!
     *
     * <p>
     * Anything below this value (ABSOLUTE VALUE!!) is set to 0.
     * </p>
     */
    public double deadzoneNegative;

    /**
     * The motor's POSITIVE and NEGATIVE deadzone.
     *
     * ABSOLUTE VALUE!!
     *
     * <p>
     * Anything below this value (ABSOLUTE VALUE!!) is set to 0.
     * </p>
     */
    public double deadzone;

    /**
     * The encoder's A channel.
     *
     * <p>
     * Each encoder should have two channels.
     * </p>
     */
    public int encoderChannelA;

    /**
     * The encoder's A channel.
     *
     * <p>
     * Each encoder should have two channels.
     * </p>
     */
    public int encoderChannelB;

    /**
     * The encoder's PID center. Not really used.
     */
    public double pidCenter;

    /**
     * The encoder's PID CPR. Also not really used.
     */
    public double pidCpr;

    /**
     * The motor's direction.
     *
     * <p>
     * This acts as another multiplier.
     * <ul>
     *     <li>Positive has another multiplier of x+1</li>
     *     <li>Positive has another multiplier of x-1</li>
     * </ul>
     * </p>
     */
    public Direction direction;

    /**
     * The motor's type.
     *
     * <p>
     * PUTTING THE WRONG TYPE MAY CAUSE PERMANENT DAMAGE TO THE MOTOR OR
     * MOTOR CONTROLLER. MAKE SURE YOU'RE RUNNING THE MOTOR IN THE CORRECT
     * MODE, OR YOU MAY SEE UNINTENTIONAL CONSEQUENCES.
     * </p>
     */
    public Type type;

    /**
     * Does the motor use separate positive and negative multipliers?
     */
    public boolean usesIndividualMultipliers = false;

    /**
     * Does the motor use separate positive and negative deadzones?
     */
    public boolean usesIndividualDeadzones = false;

    /**
     * Does the motor use a pre-multiplier?
     */
    public boolean usesPreMultiplier = false;

    /**
     * Does the motor use an encoder?
     */
    public boolean usesEncoder = false;

    /**
     * Does the motor use a PID?
     */
    public boolean usesPid = false;

    public MotorConfig(int id,
                       Type type) {
        this(
                id,
                MULTIPLIER,
                MIN,
                MAX,
                DEADZONE,
                DIRECTION,
                type
        );
    }

    public MotorConfig(int id,
                       double multiplier,
                       double min,
                       double max,
                       double deadzone,
                       Direction direction,
                       Type type) {
        this(
                id,
                multiplier,
                min,
                max,
                deadzone,
                direction,
                type,
                ENCODER_ID,
                ENCODER_ID
        );
    }

    public MotorConfig(int id,
                       double multiplier,
                       double min,
                       double max,
                       double deadzone,
                       Direction direction,
                       Type type,
                       int encoderChannelA,
                       int encoderChannelB) {
        this(
                id,
                multiplier,
                min,
                max,
                deadzone,
                direction,
                type,
                encoderChannelA,
                encoderChannelB,
                PID_CENTER,
                PID_CPR
        );

        usesEncoder = true;
    }

    public MotorConfig(int id,
                       double multiplier,
                       double min,
                       double max,
                       double deadzone,
                       Direction direction,
                       Type type,
                       int encoderChannelA,
                       int encoderChannelB,
                       double pidCenter,
                       double pidCpr) {
        this(
                id,
                multiplier,
                multiplier,
                PRE_MULTIPLIER_MIN,
                PRE_MULTIPLIER_MAX,
                min,
                max,
                deadzone,
                deadzone,
                direction,
                type,
                encoderChannelA,
                encoderChannelB,
                pidCenter,
                pidCpr
        );
    }

    public MotorConfig(int id,
                       double multiplierPositive,
                       double multiplierNegative,
                       double preMultiplierMin,
                       double preMultiplierMax,
                       double min,
                       double max,
                       double deadzonePositive,
                       double deadzoneNegative,
                       Direction direction,
                       Type type,
                       int encoderChannelA,
                       int encoderChannelB,
                       double pidCenter,
                       double pidCpr) {
        this.id = id;
        this.multiplierPositive = multiplierPositive;
        this.multiplierNegative = multiplierNegative;
        this.preMultiplierMin = preMultiplierMin;
        this.preMultiplierMax = preMultiplierMax;
        this.min = min;
        this.max = max;
        this.deadzonePositive = deadzonePositive;
        this.deadzoneNegative = deadzoneNegative;
        this.direction = direction;
        this.type = type;
        this.encoderChannelA = encoderChannelA;
        this.encoderChannelB = encoderChannelB;
        this.pidCenter = pidCenter;
        this.pidCpr = pidCpr;

        usesEncoder = encoderChannelA != -1 && encoderChannelB != -1;
    }
}
