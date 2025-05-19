import bagel.*;

/**
 * This class extends {@code GameObject} and implements {@code Collectable}
 * and is responsible for initialising, rendering and collection by mario for hammer
 */
public class Hammer extends GameObject implements Collectable {
    private boolean isCollected = false;
    private final static double GRAVITY = 0;
    private static final double HAMMER_TERMINAL_VELOCITY = 0;
    private final static int OFFSCREEN = -1000;

    /**
     * Initialising hammer with position coordinates, sprite and gravity values
     * @param centreX centre x coordinate
     * @param centreY centre y coordinate
     */
    public Hammer(double centreX, double centreY) {
        super(centreX, centreY, new Image("res/hammer.png") , GRAVITY, HAMMER_TERMINAL_VELOCITY);
    }

    /**
     * Collects blaster and moves it offscreen
     */
    @Override
    public void collect() {
        this.isCollected = true;
        setCentreX(OFFSCREEN);
        setCentreY(OFFSCREEN);
    }

    /**
     * Renders barrels to screen if not already collected by mario
     */
    @Override
    public void display() {
        if (!isCollected) {
           getSprite().draw(getCentreX(), getCentreY());
        }
    }
}
