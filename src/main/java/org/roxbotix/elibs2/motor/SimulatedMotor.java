package org.roxbotix.elibs2.motor;

public class SimulatedMotor extends Motor {
    long last = System.currentTimeMillis();
    long del = 0;
    long count = 0;
    Thread encoderThread;

    public SimulatedMotor(MotorConfig motorConfig) {
        super(motorConfig);

        encoderThread = new Thread(
                () -> {
                    while (true) {
                        Thread.onSpinWait();

                        del = System.currentTimeMillis() - last;

                        double c = getPower() * del * 2;
                        long r = (int) Math.round(c);

                        count += r;
                        getEncoder().setCountOffset((int) count);
                    }
                }
        );
    }
}
