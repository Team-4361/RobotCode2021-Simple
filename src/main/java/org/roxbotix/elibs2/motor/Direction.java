package org.roxbotix.elibs2.motor;

/**
 * A motor direction.
 *
 * @author Colin Robertson
 */
public enum Direction {
    /**
     * Go forwards... how shocking.
     */
    FORWARDS(true),

    /**
     * You definitely couldn't have guessed based on the name here.
     *
     * <p>
     * Backwards, does, in fact, mean... backwards!
     * </p>
     */
    BACKWARDS(false);

    private final boolean b;

    Direction(boolean b) {
        this.b = b;
    }

    public boolean get() {
        return b;
    }
}
