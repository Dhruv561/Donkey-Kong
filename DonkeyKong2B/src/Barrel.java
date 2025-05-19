import bagel.*;

/**
 * This class {@code GameObject} and is responsible for initialising,
 * rendering and entity interaction for all barrels.
 */
public class Barrel extends GameObject {
    private final static double GRAVITY = 0.4;
    private static final double BARREL_TERMINAL_VELOCITY = 5.0;

    /**
     * Initialising barrel with position coordinates, sprite and gravity values
     * @param centreX centre x coordinate
     * @param centreY centre y coordinate
     */
    public Barrel(double centreX, double centreY) {
        super(centreX, centreY, new Image("res/barrel.png") , GRAVITY, BARREL_TERMINAL_VELOCITY);
    }

    /**
     * Updates barrel position by making it fall to platform and
     * rendering it to screen
     * @param platforms array of all platforms
     */
    public void update(Platform[] platforms) {
        fallToPlatform(platforms);
        display();
    }
}
