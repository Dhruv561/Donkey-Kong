import bagel.*;

/**
 * This class {@code GameObject} and is responsible for initialising,
 * rendering, moving and handling entity interaction for bananas.
 */
public class Banana extends GameObject {
    private boolean isRight;
    private final double startingX;
    private final static int BANANA_GRAVITY = 0;
    private final static int BANANA_TERMINAL_VELOCITY = 0;
    private final static double MOVEMENT_VELOCITY = 1.8;
    private final static int MAXIMUM_PIXELS = 300;

    /**
     * Initialising banana with position coordinates, direction, sprite and gravity values
     * @param centreX centre x coordinate
     * @param centreY centre y coordinate
     * @param isRight is facing right
     */
    public Banana(double centreX, double centreY, boolean isRight) {
        super(centreX, centreY, new Image("res/banana.png"), BANANA_GRAVITY, BANANA_TERMINAL_VELOCITY);
        this.isRight = isRight;
        this.startingX = centreX;
    }

    /**
     * Displays banana if not destroyed
     */
    @Override
    public void display() {
        if (!isDestroyed()) {
            getSprite().draw(getCentreX(), getCentreY());
        }
    }

    /**
     * Updates and renders bananas position if not destroyed, moving left
     * or right based on its direction for at most MAXIMUM_PIXELS amount
     * before being auto destroyed. If banana hits mario, both mario and
     * bullet get destroyed.
     * @param mario mario
     */
    public void update(Mario mario) {
        if (isDestroyed()) {
            return;
        }
        if (isRight) {
            setCentreX(getCentreX() + MOVEMENT_VELOCITY);
        } else {
            setCentreX(getCentreX() - MOVEMENT_VELOCITY);
        }
        if (isTouching(mario)) {
            mario.hit();
            destroy();
        }
        if (Math.abs(getCentreX() - startingX) >= MAXIMUM_PIXELS) {
            destroy();
        }
        display();
    }
}
