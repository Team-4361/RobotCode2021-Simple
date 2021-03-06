package frc.robot.subsystems.swerve.chassis;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import frc.robot.subsystems.swerve.module.SwerveEncodedModule;
import me.wobblyyyy.edt.StaticArray;

public class Suppliers {
    private final SwerveEncodedModule fr;
    private final SwerveEncodedModule fl;
    private final SwerveEncodedModule br;
    private final SwerveEncodedModule bl;
    private SwerveModuleState[] states;
    private final StaticArray<Supplier<Double>> velocitySuppliers;
    private final StaticArray<Supplier<Double>> powerSuppliers;

    public Suppliers(SwerveEncodedModule fr, 
                     SwerveEncodedModule fl, 
                     SwerveEncodedModule br, 
                     SwerveEncodedModule bl) {
        this.fr = fr;
        this.fl = fl;
        this.br = br;
        this.bl = bl;

        velocitySuppliers = new StaticArray<Supplier<Double>>(
            this::vfr,
            this::vfl,
            this::vbr,
            this::vbl
        );

        powerSuppliers = new StaticArray<Supplier<Double>>(
            this::pfr,
            this::pfl,
            this::pbr,
            this::pbl
        );
    }

    public void setStates(SwerveModuleState[] states) {
        this.states = states;
    }

    public double pfr() {
        return states[1].speedMetersPerSecond;
    }

    public double pfl() {
        return states[0].speedMetersPerSecond;
    }

    public double pbr() {
        return states[3].speedMetersPerSecond;
    }

    public double pbl() {
        return states[2].speedMetersPerSecond;
    }

    public double vfr() {
        return fr.getDriveVelocity();
    }

    public double vfl() {
        return fl.getDriveVelocity();
    }

    public double vbr() {
        return br.getDriveVelocity();
    }

    public double vbl() {
        return bl.getDriveVelocity();
    }

    public StaticArray<Supplier<Double>> getVelocitySuppliers() {
        return velocitySuppliers;
    }

    public StaticArray<Supplier<Double>> getPowerSuppliers() {
        return powerSuppliers;
    }
}
