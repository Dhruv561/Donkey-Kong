import bagel.*;

/**
 * This class {@code GameObject} and is responsible for initialising ladders,
 * updating their position and rendering it to the screen
 */
public class Ladder extends GameObject {
    private final static double LADDER_GRAVITY = 0.25;
    private static final double LADDER_TERMINAL_VELOCITY = 5.0;

    /**
     * Initialises ladder based on position coordinates, sprite and gravity values
     * @param centreX centre x coordinate
     * @param centreY centre y coordinate
     */
    public Ladder(double centreX, double centreY) {
        super(centreX, centreY, new Image("res/ladder.png"), LADDER_GRAVITY, LADDER_TERMINAL_VELOCITY);
    }

    /**
     * Updates ladder position by making it snap to platform and rendering to screen
     * @param platforms array of all platfroms
     */
    public void update(Platform[] platforms) {
        fallToPlatform(platforms);
        display();
    }
}
