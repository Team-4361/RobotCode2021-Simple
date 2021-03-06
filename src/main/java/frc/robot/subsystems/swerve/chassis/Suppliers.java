package frc.robot.subsystems.swerve.chassis;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import frc.robot.subsystems.swerve.module.SwerveEncodedModule;
import me.wobblyyyy.edt.StaticArray;
import me.wobblyyyy.edt.StaticMap;

/**
 * Make use of the utility normalization functionality provided by EDT 1.1.0.
 * All of the functionality behind normalization and different types of
 * {@code Arrayable} objects is documented quite well on the official GitHub
 * page of EDT. If you're confused about what's going on here, check that out.
 * 
 * <p>
 * <a href="https://github.com/Wobblyyyy/edt">Link here</a> 
 * </p>
 * 
 * <p>
 * The design methodology here is utilize suppliers to constantly get the most
 * updated value without having to re-declare or re-allocate objects in memory.
 * Velocities are sanity-checked by ensuring they're all at the very least "1",
 * just to make sure that the motors can start to spin at the same time.
 * </p>
 */
public class Suppliers {
    private enum Modules {
        FR,
        FL,
        BR,
        BL
    };

    private final StaticMap<Modules, SwerveEncodedModule> modules = 
        new StaticMap<>(new StaticArray<Modules>(
            Modules.FR, 
            Modules.FL, 
            Modules.BR, 
            Modules.BL
        ));

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
        final double v;
        if ((v = fr.getDriveVelocity()) <= 100) return 1;
        else return v;
    }

    public double vfl() {
        final double v;
        if ((v = fl.getDriveVelocity()) <= 100) return 1;
        else return v;
    }

    public double vbr() {
        final double v;
        if ((v = br.getDriveVelocity()) <= 100) return 1;
        else return v;
    }

    public double vbl() {
        final double v;
        if ((v = bl.getDriveVelocity()) <= 100) return 1;
        else return v;
    }

    public StaticArray<Supplier<Double>> getVelocitySuppliers() {
        return velocitySuppliers;
    }

    public StaticArray<Supplier<Double>> getPowerSuppliers() {
        return powerSuppliers;
    }
}
