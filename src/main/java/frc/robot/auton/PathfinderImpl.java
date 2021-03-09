package frc.robot.auton;

import frc.robot.subsystems.swerve.chassis.SwerveChassis;
import me.wobblyyyy.pathfinder.api.Pathfinder;
import me.wobblyyyy.pathfinder.config.SimpleConfig;
import me.wobblyyyy.pathfinder.core.Followers;
import me.wobblyyyy.pathfinder.maps.frc.EmptyFRC;

public class PathfinderImpl {
    public SimpleConfig config;
    public Pathfinder pathfinder;

    public PathfinderImpl(SwerveChassis chassis) {
        config = new SimpleConfig() {{
            setDrive(chassis);
            setOdometry(chassis.getOdometry());
            setFollower(Followers.LINEAR);
            setGapX(23.5);
            setGapY(23.5);
            setMap(new EmptyFRC());
        }};
        pathfinder = new Pathfinder(config);
    }

    public Pathfinder getPathfinder() {
        return pathfinder;
    }
}
