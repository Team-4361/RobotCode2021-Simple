package org.roxbotix.elibs2.curve;

import org.junit.Test;

import java.util.HashMap;

public class ActorTester extends NumberPercentActor {
    public ActorTester() {
        super(
                new HashMap<>() {{
                    put(0.0, 0.0);
                    put(50.0, 25.0);
                    put(100.0, 100.0);
                }},
                true
        );
    }

    @Test
    public void testValue() {
        System.out.println("At 25 percent: " + getValue(25.0));
        System.out.println("At 50 percent: " + getValue(50.0));
        System.out.println("At 75 percent: " + getValue(75.0));
        System.out.println("At 90 percent: " + getValue(90.0));
        System.out.println("At 100 percent: " + getValue(100.0));
    }
}
