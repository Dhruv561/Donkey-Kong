import bagel.*;

public class Platform extends GameObject {
    private static final double PLATFORM_TERMINAL_VELOCITY = 0;
    private static final double GRAVITY = 0;

    public Platform(double centreX, double centreY) {
        super(centreX, centreY, new Image("res/platform.png"), GRAVITY, PLATFORM_TERMINAL_VELOCITY);
    }
}
