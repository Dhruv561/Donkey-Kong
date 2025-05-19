import bagel.*;

/**
 * This class extends {@code GameObject} and implements {@code Collectable}
 * and is responsible for initialising, rendering and collection by mario for all blasters
 */
public class Blaster extends GameObject implements Collectable {
    private boolean isCollected = false;
    private final static int OFFSCREEN = -100;
    private final static int BLASTER_GRAVITY = 0;
    private final static int BLASTER_TERMINAL_VELOCITY = 0;

    /**
     * Initialising blaster with position coordinates, sprite and gravity values
     * @param centreX centre x coordinate
     * @param centreY centre y coordinate
     */
    public Blaster(double centreX, double centreY) {
        super(centreX, centreY, new Image("res/blaster.png"), BLASTER_GRAVITY, BLASTER_TERMINAL_VELOCITY);
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

    /**
     * Collects blaster and moves it offscreen
     */
    @Override
    public void collect() {
        this.isCollected = true;
        setCentreX(OFFSCREEN);
        setCentreY(OFFSCREEN);
    }
}
