package org.roxbotix.elibs3.main;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Utility annotation used for accessing different OpModes without having
 * to manually register all of the different OpModes.
 *
 * <p>
 * Operation modes are changeable via the ModeManager system.
 * </p>
 *
 * @author Colin Robertson
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RobotMode {
    /**
     * The name of the robot mode.
     *
     * <p>
     * This name is case-sensitive, meaning that a potentially forgotten
     * capital letter can change whether or not the robot even works.
     * </p>
     *
     * @return the robot mode's name.
     */
    String name() default "";
}
