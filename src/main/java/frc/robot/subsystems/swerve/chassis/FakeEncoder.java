package frc.robot.subsystems.swerve.chassis;

import frc.robot.subsystems.swerve.encoder.SwerveEncoder;
import me.wobblyyyy.pathfinder.robot.Encoder;

public class FakeEncoder implements Encoder {
    private final SwerveEncoder encoder;
    private final int cpr;
    private final double dt;
    private final double td;
    private double count = 0;
    private double lastPos = 0;

    public FakeEncoder(SwerveEncoder encoder, int cpr) {
        this.encoder = encoder;
        this.cpr = cpr;
        this.dt = cpr / 360;
        this.td = 360 / cpr;
    }

    private int degreesToTicks(double degrees) {
        return (int) Math.round(degrees * dt);
    }

    private double ticksToDegrees(int ticks) {
        return ticks * td;
    }

    private void update(double newPos) {
        double difference = newPos - lastPos;
        double differenceInCounts = degreesToTicks(Math.toDegrees(difference));

        count += differenceInCounts;
        
        lastPos = newPos;
    }

    @Override
    public int getCount() {
        update(encoder.getPos());
        return (int) Math.round(count);
    }

    @Override
    public double getCpr() {
        return cpr;
    }
}
