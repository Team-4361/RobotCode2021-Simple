package frc.robot.subsystems.swerve.chassis;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import frc.robot.RobotMap;
import frc.robot.subsystems.swerve.module.SwerveEncodedModule;
import me.wobblyyyy.edt.StaticArray;
import me.wobblyyyy.pathfinder.drive.Drive;
import me.wobblyyyy.pathfinder.geometry.Angle;
import me.wobblyyyy.pathfinder.geometry.HeadingPoint;
import me.wobblyyyy.pathfinder.geometry.Point;
import me.wobblyyyy.pathfinder.kinematics.RTransform;
import me.wobblyyyy.pathfinder.kinematics.SpeedConverter;
import me.wobblyyyy.pathfinder.kinematics.SwerveKinematics;
import me.wobblyyyy.pathfinder.kinematics.SwerveModuleState;
import me.wobblyyyy.pathfinder.kinematics.SwerveOdometry;
import me.wobblyyyy.pathfinder.math.GearRatio;
import me.wobblyyyy.pathfinder.math.RotationalVelocity;
import me.wobblyyyy.pathfinder.math.WheelSize;
import me.wobblyyyy.pathfinder.math.RotationalVelocity.Type;
import me.wobblyyyy.pathfinder.robot.Odometry;
import me.wobblyyyy.pathfinder.util.Distance;

public class EpicChassis implements Drive, Odometry {
    private static final double WHEELBASE = 23.5;
    private static final double WB2 = WHEELBASE / 2;

    private boolean isUserControlled = true;

    private SwerveKinematics kinematics;
    private SwerveOdometry odometry;

    private SwerveEncodedModule frModule;
    private SwerveEncodedModule flModule;
    private SwerveEncodedModule brModule;
    private SwerveEncodedModule blModule;
    private AHRS gyro;

    private static final GearRatio gearRatio = GearRatio.oneOutputEquals(8.16);
    private static final WheelSize wheelSize = WheelSize.diameter(4.0);

    private RotationalVelocity frVelocity;
    private RotationalVelocity flVelocity;
    private RotationalVelocity brVelocity;
    private RotationalVelocity blVelocity;

    private SpeedConverter frConverter;
    private SpeedConverter flConverter;
    private SpeedConverter brConverter;
    private SpeedConverter blConverter;
    private StaticArray<SpeedConverter> converters = new StaticArray<>(
        frConverter,
        flConverter,
        brConverter,
        blConverter
    );

    private static final int CPR = 4096;
    private static final RotationalVelocity.Type TYPE = Type.ROTATIONS_PER_MINUTE;

    private static final StaticArray<Point> wheelPositions = new StaticArray<>(
        new Point(+WB2, +WB2),
        new Point(-WB2, +WB2),
        new Point(-WB2, -WB2),
        new Point(+WB2, -WB2)
    );

    public double getAngle() {
        return -gyro.getAngle();
    }

    public double getFrVelocity() {
        if (!frModule.isDriveInverted()) return frModule.getDriveVelocity();
        return frModule.getDriveVelocity() * -1.0;
    }

    public double getFlVelocity() {
        if (!flModule.isDriveInverted()) return flModule.getDriveVelocity();
        return flModule.getDriveVelocity() * -1.0;
    }

    public double getBrVelocity() {
        if (!brModule.isDriveInverted()) return brModule.getDriveVelocity();
        return brModule.getDriveVelocity() * -1.0;
    }

    public double getBlVelocity() {
        if (!blModule.isDriveInverted()) return blModule.getDriveVelocity();
        return blModule.getDriveVelocity() * -1.0;
    }

    {
        kinematics = new SwerveKinematics(wheelPositions);
        odometry = new SwerveOdometry(kinematics, Angle.fromDegrees(0), new HeadingPoint(0, 0, 0));

        frModule = new SwerveEncodedModule(
            RobotMap.FR_TURN, RobotMap.FR_DRIVE, 
            CPR, CPR,
            true, true
        );
        flModule = new SwerveEncodedModule(
            RobotMap.FL_TURN, RobotMap.FL_DRIVE, 
            CPR, CPR,
            true, true
        );
        brModule = new SwerveEncodedModule(
            RobotMap.BR_TURN, RobotMap.BR_DRIVE, 
            CPR, CPR,
            true, true
        );
        blModule = new SwerveEncodedModule(
            RobotMap.BL_TURN, RobotMap.BL_DRIVE, 
            CPR, CPR,
            true, true
        );

        frVelocity = new RotationalVelocity(
            this::getFrVelocity, 
            TYPE
        );
        flVelocity = new RotationalVelocity(
            this::getFlVelocity, 
            TYPE
        );
        brVelocity = new RotationalVelocity(
            this::getBrVelocity, 
            TYPE
        );
        blVelocity = new RotationalVelocity(
            this::getBlVelocity, 
            TYPE
        );

        frConverter = new SpeedConverter(frVelocity, wheelSize, gearRatio);
        flConverter = new SpeedConverter(flVelocity, wheelSize, gearRatio);
        brConverter = new SpeedConverter(brVelocity, wheelSize, gearRatio);
        blConverter = new SpeedConverter(blVelocity, wheelSize, gearRatio);

        gyro = new AHRS(SPI.Port.kMXP);
    }

    public EpicChassis() {

    }

    public HeadingPoint getPos() {
        return odometry.getPosition();
    }

    public void update() {

    }

    public void enableUserControl() {
        isUserControlled = true;

        frModule.enableUserControl();
        flModule.enableUserControl();
        brModule.enableUserControl();
        blModule.enableUserControl();
    }

    public void disableUserControl() {
        isUserControlled = false;

        frModule.disableUserControl();
        flModule.disableUserControl();
        brModule.disableUserControl();
        blModule.disableUserControl();
    }

    private void setStates(RTransform transform) {
        StaticArray<SwerveModuleState> states = kinematics.getStates(transform);

        frModule.setState(states.get(0), isUserControlled);
        flModule.setState(states.get(1), isUserControlled);
        brModule.setState(states.get(2), isUserControlled);
        blModule.setState(states.get(3), isUserControlled);

        StaticArray<SwerveModuleState> realStates = 
                SpeedConverter.getSwerveModuleStates(
            states, 
            converters
        );

        odometry.update(Angle.fromDegrees(getAngle()), realStates);
    }

    public void drive(double power, double angle) {
        HeadingPoint start = new HeadingPoint(0, 0, 0);
        HeadingPoint end = new HeadingPoint(Distance.inDirection(start, power, angle));
        drive(start, end, power);
    }

    public void drive(HeadingPoint start, HeadingPoint end, double power) {
        ChassisSpeeds speeds = ChassisSpeeds.fromFieldRelativeSpeeds(
            end.getX(), 
            end.getY(), 
            0, 
            Rotation2d.fromDegrees(getAngle())
        );

        RTransform transform = new RTransform(
            speeds.vxMetersPerSecond,
            speeds.vyMetersPerSecond,
            Angle.fromRadians(speeds.omegaRadiansPerSecond)
        );

        setStates(transform);
    }
}
