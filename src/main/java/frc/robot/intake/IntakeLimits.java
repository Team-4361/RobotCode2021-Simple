package frc.robot.intake;

import edu.wpi.first.wpilibj.DigitalInput;

public class IntakeLimits {
    private final DigitalInput top;
    private final DigitalInput bottom;

    public IntakeLimits(int topChannel,
                        int bottomChannel) {
        this.top = new DigitalInput(topChannel);
        this.bottom = new DigitalInput(bottomChannel);
    }

    public boolean atTop() {
        return top.get();
    }

    public boolean atBottom() {
        return bottom.get();
    }
}
