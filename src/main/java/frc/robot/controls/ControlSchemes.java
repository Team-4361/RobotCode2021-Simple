package frc.robot.controls;

import frc.robot.Constants;

/**
 * Provide various control schemes so that they're very easy to switch on
 * the fly.
 *
 * <p>
 * By default, control schemes can be switched by simply instantiating a
 * new control scheme. However, in the interest of simplifying code, and in
 * the interest of making it easier for someone who's never seen the code to
 * figure out how to change something, this enum is used.
 * </p>
 *
 * @author Colin Robertson
 * @since 0.0.0
 */
public enum ControlSchemes {
    GENERIC(new Generic(
            Constants.PORT_JOYSTICK_1,
            Constants.PORT_JOYSTICK_2,
            Constants.PORT_CONTROLLER
    )),
    COLIN(new Colin(
            Constants.PORT_JOYSTICK_1,
            Constants.PORT_JOYSTICK_2,
            Constants.PORT_CONTROLLER
    ));

    private ControlScheme scheme;

    ControlSchemes(ControlScheme scheme) {
        this.scheme = scheme;
    }

    public ControlScheme getScheme() {
        return scheme;
    }
}
