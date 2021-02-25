package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.DriveCommand;
import org.frcteam2910.common.drivers.Gyroscope;
import org.frcteam2910.common.drivers.SwerveModule;
import org.frcteam2910.common.math.Vector2;
import org.frcteam2910.common.robot.drivers.Mk2SwerveModuleBuilder;
import org.frcteam2910.common.robot.drivers.NavX;

public class DrivetrainSubsystem extends Subsystem {
    private static final double TRACKWIDTH = 23.5;
    private static final double WHEELBASE = 23.5;

    private static final double FRONT_LEFT_ANGLE_OFFSET = -Math.toRadians(0);
    private static final double FRONT_RIGHT_ANGLE_OFFSET = -Math.toRadians(0);
    private static final double BACK_LEFT_ANGLE_OFFSET = -Math.toRadians(0);
    private static final double BACK_RIGHT_ANGLE_OFFSET = -Math.toRadians(0);

    private static DrivetrainSubsystem instance;

    private static final MotorType type = MotorType.kBrushless;
    private static final int cpr = 4096;
    private static final double dpt = 360 / (cpr * 1.0);

    private final CANSparkMax front_left_angle =
            new CANSparkMax(RobotMap.DRIVETRAIN_FRONT_LEFT_ANGLE_MOTOR, type);
    private final CANSparkMax front_left_drive =
            new CANSparkMax(RobotMap.DRIVETRAIN_FRONT_LEFT_DRIVE_MOTOR, type);
    private final CANSparkMax front_right_angle =
            new CANSparkMax(RobotMap.DRIVETRAIN_FRONT_RIGHT_ANGLE_MOTOR, type);
    private final CANSparkMax front_right_drive =
            new CANSparkMax(RobotMap.DRIVETRAIN_FRONT_RIGHT_DRIVE_MOTOR, type);
    private final CANSparkMax back_left_angle =
            new CANSparkMax(RobotMap.DRIVETRAIN_BACK_LEFT_ANGLE_MOTOR, type);
    private final CANSparkMax back_left_drive =
            new CANSparkMax(RobotMap.DRIVETRAIN_BACK_LEFT_DRIVE_MOTOR, type);
    private final CANSparkMax back_right_angle =
            new CANSparkMax(RobotMap.DRIVETRAIN_BACK_RIGHT_ANGLE_MOTOR, type);
    private final CANSparkMax back_right_drive =
            new CANSparkMax(RobotMap.DRIVETRAIN_BACK_RIGHT_DRIVE_MOTOR, type);

    private final CANEncoder front_left_encoder =
            front_left_angle.getAlternateEncoder(cpr);
    private final CANEncoder front_right_encoder =
            front_right_angle.getAlternateEncoder(cpr);
    private final CANEncoder back_left_encoder =
            back_left_angle.getAlternateEncoder(cpr);
    private final CANEncoder back_right_encoder =
            back_right_angle.getAlternateEncoder(cpr);

    private final double front_left_start = front_left_encoder.getPosition();
    private final double front_right_start = front_right_encoder.getPosition();
    private final double back_left_start = front_left_encoder.getPosition();
    private final double back_right_start = back_right_encoder.getPosition();

    private final SwerveModule frontLeftModule = new Mk2SwerveModuleBuilder(
            new Vector2(TRACKWIDTH / 2.0, WHEELBASE / 2.0))
            .angleEncoder(new AnalogInput(0), 0)
            .angleMotor(front_left_angle, Mk2SwerveModuleBuilder.MotorType.NEO)
            .driveMotor(front_left_drive, Mk2SwerveModuleBuilder.MotorType.NEO)
            .build();
    private final SwerveModule frontRightModule = new Mk2SwerveModuleBuilder(
            new Vector2(TRACKWIDTH / 2.0, -WHEELBASE / 2.0))
            .angleEncoder(new AnalogInput(1), 0)
            .angleMotor(front_right_angle, Mk2SwerveModuleBuilder.MotorType.NEO)
            .driveMotor(front_right_drive, Mk2SwerveModuleBuilder.MotorType.NEO)
            .build();
    private final SwerveModule backLeftModule = new Mk2SwerveModuleBuilder(
            new Vector2(-TRACKWIDTH / 2.0, WHEELBASE / 2.0))
            .angleEncoder(new AnalogInput(2), 0)
            .angleMotor(back_left_angle, Mk2SwerveModuleBuilder.MotorType.NEO)
            .driveMotor(back_left_drive, Mk2SwerveModuleBuilder.MotorType.NEO)
            .build();
    private final SwerveModule backRightModule = new Mk2SwerveModuleBuilder(
            new Vector2(-TRACKWIDTH / 2.0, -WHEELBASE / 2.0))
            .angleEncoder(new AnalogInput(3), 0)
            .angleMotor(back_right_angle, Mk2SwerveModuleBuilder.MotorType.NEO)
            .driveMotor(back_right_drive, Mk2SwerveModuleBuilder.MotorType.NEO)
            .build();

    private final SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
            new Translation2d(TRACKWIDTH / 2.0, WHEELBASE / 2.0),
            new Translation2d(TRACKWIDTH / 2.0, -WHEELBASE / 2.0),
            new Translation2d(-TRACKWIDTH / 2.0, WHEELBASE / 2.0),
            new Translation2d(-TRACKWIDTH / 2.0, -WHEELBASE / 2.0)
    );

    private final Gyroscope gyroscope = new NavX(SPI.Port.kMXP);

    public DrivetrainSubsystem() {
        gyroscope.calibrate();
        gyroscope.setInverted(true); // You might not need to invert the gyro

        frontLeftModule.setName("Front Left");
        frontRightModule.setName("Front Right");
        backLeftModule.setName("Back Left");
        backRightModule.setName("Back Right");

        CANEncoder encoder;
    }

    public static DrivetrainSubsystem getInstance() {
        if (instance == null) {
            instance = new DrivetrainSubsystem();
        }

        return instance;
    }

    @Override
    public void periodic() {
        frontLeftModule.updateSensors();
        frontRightModule.updateSensors();
        backLeftModule.updateSensors();
        backRightModule.updateSensors();

        double frp = front_right_encoder.getPosition();
        double flp = front_left_encoder.getPosition();
        double brp = back_right_encoder.getPosition();
        double blp = back_left_encoder.getPosition();

        double frpd = (frp - front_right_start) * dpt;
        double flpd = (flp - front_left_start) * dpt;
        double brpd = (brp - back_right_start) * dpt;
        double blpd = (blp - back_left_start) * dpt;

        SmartDashboard.putNumber("frd", frpd);
        SmartDashboard.putNumber("fld", flpd);
        SmartDashboard.putNumber("brd", brpd);
        SmartDashboard.putNumber("bld", blpd);

        // SmartDashboard.putNumber("Gyroscope Angle", gyroscope.getAngle().toDegrees());

        frontLeftModule.updateState(TimedRobot.kDefaultPeriod);
        frontRightModule.updateState(TimedRobot.kDefaultPeriod);
        backLeftModule.updateState(TimedRobot.kDefaultPeriod);
        backRightModule.updateState(TimedRobot.kDefaultPeriod);
    }

    public void drive(Translation2d translation,
                      double rotation,
                      boolean fieldOriented) {
        rotation *= 2.0 / Math.hypot(WHEELBASE, TRACKWIDTH);
        ChassisSpeeds speeds;

        if (fieldOriented) {
            speeds = ChassisSpeeds.fromFieldRelativeSpeeds(
                    translation.getX(),
                    translation.getY(),
                    rotation,
                    Rotation2d.fromDegrees(gyroscope.getAngle().toDegrees())
            );
        } else {
            speeds = new ChassisSpeeds(
                    translation.getX(),
                    translation.getY(),
                    rotation
            );
        }

        SwerveModuleState[] states = kinematics.toSwerveModuleStates(speeds);

        frontLeftModule.setTargetVelocity(
                states[0].speedMetersPerSecond,
                states[0].angle.getRadians()
        );
        frontRightModule.setTargetVelocity(
                states[1].speedMetersPerSecond,
                states[1].angle.getRadians()
        );
        backLeftModule.setTargetVelocity(
                states[2].speedMetersPerSecond,
                states[2].angle.getRadians()
        );
        backRightModule.setTargetVelocity(
                states[3].speedMetersPerSecond,
                states[3].angle.getRadians()
        );
    }

    public void resetGyroscope() {
        gyroscope.setAdjustmentAngle(gyroscope.getUnadjustedAngle());
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DriveCommand());
    }
}
