import bagel.*;

/**
 * This class extends {@code Entity} and represents Donkey Kong.
 * It is responsible for tracking donkey kong health, falling to
 * platform and rendering on the screen.
 */
public class DonkeyKong extends Entity {
    private int health = 5;
    private final static double GRAVITY = 0.4;
    private final static double DONKEY_TERMINAL_VELOCITY = 5;

    /**
     * Initialises donkey kong based on position coordinates, left and right facing sprites
     * and gravity values
     * @param centreX centre x coordinate
     * @param centreY centre y coordinate
     */
    public DonkeyKong(double centreX, double centreY) {
        super(centreX, centreY, new Image("res/donkey_kong.png"), new Image("res/donkey_kong.png"),
                GRAVITY, DONKEY_TERMINAL_VELOCITY);
    }

    /**
     * If donkey kong is hit by bullet lower health
     */
    public void hit() {
        this.health -= 1;
    }

    /**
     * Returns donkey kong health
     * @return donkey kong health
     */
    public int getHealth () {
        return this.health;
    }

    /**
     * Empty implementation of abstract method from Entity class
     */
    @Override
    public void moveHorizontal() {}

    /**
     * Updates donkey kong's position by ensuring he falls to platform
     * and renders him on the platform
     * @param platforms array of all platforms
     */
    public void update(Platform[] platforms) {
        fallToPlatform(platforms);
        display();
    }
}
