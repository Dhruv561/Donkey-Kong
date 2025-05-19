import bagel.*;

/**
 * This class {@code GameObject} and is responsible for initialising platforms
 */
public class Platform extends GameObject {
    private static final double PLATFORM_TERMINAL_VELOCITY = 0;
    private static final double PLATFORM_GRAVITY = 0;

    /**
     * Initialises platform based on position coordinates, sprite and gravity values
     * @param centreX centre x coordinate
     * @param centreY centre y coordinate
     */
    public Platform(double centreX, double centreY) {
        super(centreX, centreY, new Image("res/platform.png"), PLATFORM_GRAVITY, PLATFORM_TERMINAL_VELOCITY);
    }
}
