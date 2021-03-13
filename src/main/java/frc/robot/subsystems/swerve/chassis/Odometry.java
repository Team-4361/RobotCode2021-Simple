package frc.robot.subsystems.swerve.chassis;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import frc.robot.subsystems.swerve.module.SwerveEncodedModule;
import me.wobblyyyy.edt.StaticArray;
import me.wobblyyyy.pathfinder.geometry.Angle;
import me.wobblyyyy.pathfinder.geometry.HeadingPoint;
import me.wobblyyyy.pathfinder.kinematics.SpeedConverter;
import me.wobblyyyy.pathfinder.kinematics.SwerveKinematics;
import me.wobblyyyy.pathfinder.kinematics.SwerveModuleState;
import me.wobblyyyy.pathfinder.kinematics.SwerveOdometry;
import me.wobblyyyy.pathfinder.math.GearRatio;
import me.wobblyyyy.pathfinder.math.RotationalVelocity;
import me.wobblyyyy.pathfinder.math.WheelSize;

public class Odometry implements me.wobblyyyy.pathfinder.robot.Odometry {
    private final SwerveOdometry odometry;
    private final SwerveKinematics kinematics;

    private final GearRatio gearRatio = GearRatio.oneOutputEquals(8.16);
    private final WheelSize wheelSize = WheelSize.diameter(4.0);

    private SwerveEncodedModule frModule;
    private SwerveEncodedModule flModule;
    private SwerveEncodedModule brModule;
    private SwerveEncodedModule blModule;
    private Supplier<Double> gyroSupplier;

    private final RotationalVelocity frV = new RotationalVelocity(
        this::getFrVelocity, 
        RotationalVelocity.Type.ROTATIONS_PER_MINUTE
    );
    private final RotationalVelocity flV = new RotationalVelocity(
        this::getFlVelocity, 
        RotationalVelocity.Type.ROTATIONS_PER_MINUTE
    );
    private final RotationalVelocity brV = new RotationalVelocity(
        this::getBrVelocity, 
        RotationalVelocity.Type.ROTATIONS_PER_MINUTE
    );
    private final RotationalVelocity blV = new RotationalVelocity(
        this::getBlVelocity, 
        RotationalVelocity.Type.ROTATIONS_PER_MINUTE
    );

    private final SpeedConverter fr = new SpeedConverter(
        frV, 
        wheelSize, 
        gearRatio
    );
    private final SpeedConverter fl = new SpeedConverter(
        flV, 
        wheelSize, 
        gearRatio
    );
    private final SpeedConverter br = new SpeedConverter(
        brV, 
        wheelSize, 
        gearRatio
    );
    private final SpeedConverter bl = new SpeedConverter(
        blV, 
        wheelSize, 
        gearRatio
    );

    public Odometry(SwerveKinematics kinematics,
                    Angle gyroOffset,
                    HeadingPoint startPoint) {
        this.kinematics = kinematics;
        odometry = new SwerveOdometry(kinematics, gyroOffset, startPoint);
    }

    public void setModules(SwerveEncodedModule frModule, 
                           SwerveEncodedModule flModule, 
                           SwerveEncodedModule brModule, 
                           SwerveEncodedModule blModule) {
        this.frModule = frModule;
        this.flModule = flModule;
        this.brModule = brModule;
        this.blModule = blModule;
    }

    public void setGyroSupplier(Supplier<Double> supplier) {
        this.gyroSupplier = supplier;
    }

    public void update(StaticArray<SwerveModuleState> states) {
        double gyroAngle = gyroSupplier.get();

        StaticArray<SwerveModuleState> real = new StaticArray<>() {{
            set(0, new SwerveModuleState(
                fl.inchesPerSecond(), 
                states.get(0).getTurnAngle())
            );
            set(1, new SwerveModuleState(
                fr.inchesPerSecond(), 
                states.get(1).getTurnAngle())
            );
            set(2, new SwerveModuleState(
                bl.inchesPerSecond(), 
                states.get(2).getTurnAngle())
            );
            set(3, new SwerveModuleState(
                br.inchesPerSecond(), 
                states.get(3).getTurnAngle())
            );
        }};

        odometry.update(Angle.fromDegrees(gyroAngle), real);
    }

    public double getFrVelocity() {
        return frModule.isDriveInverted() ? 
            frModule.getDriveVelocity() * -1 : 
            frModule.getDriveVelocity();
    }

    public double getFlVelocity() {
        return flModule.isDriveInverted() ? 
            flModule.getDriveVelocity() * -1 : 
            flModule.getDriveVelocity();
    }

    public double getBrVelocity() {
        return brModule.isDriveInverted() ? 
            brModule.getDriveVelocity() * -1 : 
            brModule.getDriveVelocity();
    }

    public double getBlVelocity() {
        return blModule.isDriveInverted() ? 
            blModule.getDriveVelocity() * -1 : 
            blModule.getDriveVelocity();
    }

    @Override 
    public HeadingPoint getPos() {
        return odometry.getPosition();
    }

    @Override 
    public void update() {

    }
}
