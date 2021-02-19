package org.roxbotix.elibs3.main;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.Main;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Set;

/**
 * Internal utility class used for managing a list of modes based on
 * annotation type (or lack thereof) and annotation properties.
 *
 * <p>
 * unless you really know what you're doing you should definitely go away lmao
 * </p>
 *
 * <p>
 * Classes and polymorphism are really annoying. Once this class is working,
 * try to... not mess anything up.
 * </p>
 *
 * @author Colin Robertson
 */
public class ModeManager {
    /**
     * A tag, used in printing messages.
     */
    private static final String tag = "[MODES] ";

    /**
     * A reference to the Reflections library.
     */
    public static Reflections reflections = new Reflections("frc");

    /**
     * All of the found modes.
     */
    public static HashMap<String, Class<?>> modes = new HashMap<>();

    /*
     * Statically-executed code.
     *
     * This just does a couple of things.
     *
     * 1. Get all classes annotated as a RobotMode.
     * 2. Add those classes to the HashMap of classes.
     * 3. Look pretty.
     */
    static {
        Set<Class<?>> annotatedClasses =
                reflections.getTypesAnnotatedWith(RobotMode.class);

        for (Class<?> c : annotatedClasses) {
            modes.put(
                    c.getAnnotation(RobotMode.class).name(),
                    c
            );
        }
    }

    /**
     * Write an error to the console.
     */
    private static void writeError() {
        System.out.println(tag + "Initialization failed!");
        System.out.println(tag + "Could not find any @RobotModes with " +
                "the ID \"" + Main.ROBOT_MODE + "\"");
        System.out.println(tag + "The following @RobotMode(s) were found.");
        for (HashMap.Entry<String, Class<?>> e : modes.entrySet()) {
            System.out.println(tag + "- " + e.getKey() +
                    " (" + e.getValue().getPackageName() + ")");
        }
        System.out.println();
        System.out.println("*********************************************");
        System.out.println("* Remember: modes are case-sensitive and    *");
        System.out.println("* whitespace is included in those names.    *");
        System.out.println("* If you're reading this, you should change *");
        System.out.println("* your target mode to one of those in the   *");
        System.out.println("* list right above this message. :)         *");
        System.out.println("*********************************************");
        System.out.println();
    }

    /**
     * Get a TimedRobot based on Main.java's selected robot mode.
     *
     * @return a TimedRobot instance.
     */
    public static TimedRobot get() {
        System.out.println(tag + "Looking for @RobotMode with the given ID " +
                Main.ROBOT_MODE
        );

        if (modes.containsKey(Main.ROBOT_MODE)) {
            System.out.println(tag + "@RobotMode \"" +
                    Main.ROBOT_MODE + "\" located!");
        } else {
            writeError();
        }

        Class<?> c = modes.get(Main.ROBOT_MODE);

        try {
            return (TimedRobot) c.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
