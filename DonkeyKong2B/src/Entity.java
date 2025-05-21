import bagel.*;

/**
 * This class extends {@code GameObject} and implements {@code Moveable} and is responsible for
 * initialising game objects which are entities that can move, change directions and are actual
 * characters in the game such as mario, donkey kong and the monkeys. This class is responsible
 * for handing boundary detection to ensure sprites don't move offscreen, updating sprite direction
 * and finding platform entities are currently standing on.
 */
public abstract class Entity extends GameObject {
    private final Image RIGHT_SPRITE;
    private final Image LEFT_SPRITE;
    private boolean isRight = true;

    /**
     * Initialises entity based of position coordinates, left and right facing sprites and gravity values
     * @param centreX centre x coordinate
     * @param centerY centre y coordinate
     * @param leftImage left facing sprite
     * @param rightImage right facing sprite
     * @param gravity acceleration from gravity
     * @param terminalVelocity maximum velocity
     */
    public Entity(double centreX, double centerY, Image leftImage, Image rightImage,
                  double gravity, double terminalVelocity) {
        super(centreX, centerY, rightImage, gravity, terminalVelocity);
        this.RIGHT_SPRITE = rightImage;
        this.LEFT_SPRITE = leftImage;
    }

    /**
     * Returns boolean if entity is facing right
     * @return boolean if facing right
     */
    public boolean isRight() {
        return isRight;
    }

    /**
     * Sets sprite to face left or right
     * @param right boolean if facing right direction
     */
    public void setRight(boolean right) {
        isRight = right;
    }

    /**
     * Updates sprite based on the direction its facing
     */
    protected void updateSprite() {
        setSprite(isRight ? RIGHT_SPRITE : LEFT_SPRITE); // set sprite based off whether facing right or left
    }

    /**
     * Abstract method which must be implemented in child classes from moveable interface
     * which allows for left and right movement
     */
    public abstract void moveHorizontal();

    /**
     * Calculates boundaries of window and resets position of entity to be on screen
     * if entity has passed screen boundaries (top, bottom, left and right).
     */
    public void enforceBoundaries() {
        if (getLeftX() < 0) {
            // avoid entity moving left of window
            setCentreX(getWidth() / 2);
        }
        if (getRightX() > ShadowDonkeyKong.getScreenWidth()) {
            // avoid entity moving right of window
            setCentreX(ShadowDonkeyKong.getScreenWidth() - getWidth() / 2);
        }
        if (getTopY() < 0) {
            // avoid entity moving above window
            setCentreY(getWidth() / 2);
        }
        if (getBottomY() > ShadowDonkeyKong.getScreenHeight()) {
            // avoid entity moving below window
            setCentreY(ShadowDonkeyKong.getScreenHeight() - getHeight() / 2);
        }
    }

    /**
     * Calculates and checks to see if the entity is at the edge of the window
     * boundaries or the edge of the platform the entity is currently standing on.
     * @param platforms array of all platforms
     * @return boolean of whether entity is on boundary of window or platform edge
     */
    public boolean atBoundary(Platform[] platforms) {
        boolean rightEdge = getRightX() >= ShadowDonkeyKong.getScreenWidth(); // checks if past right edge of window
        boolean leftEdge = getLeftX() <= 0; // checks if entity is past left edge of window

        Platform platform = findPlatform(platforms); // tracks platform entity is standing on
        boolean platformEdge = false; // is entity on platform edge

        if (platform != null) {
            boolean leftPlatformEdge = getLeftX() <= platform.getLeftX(); // checks if on left edge of platform
            boolean rightPlatformEdge = getRightX() >= platform.getRightX(); // checks if on right edge of platform
            platformEdge = leftPlatformEdge || rightPlatformEdge; // checks if on platform edge
        }

        return rightEdge || leftEdge || platformEdge; // returns if on edge of either window or platform
    }

    /**
     * Finds the platform which the entity is currently standing on
     * @param platforms list of all platforms
     * @return current platform
     */
    private Platform findPlatform(Platform[] platforms) {
        for (Platform platform : platforms) {
            // checks if entity's bottom is on platforms top so entity is touching platform
            if (getBottomY() == platform.getTopY()) {
                return platform;
            }
        }
        return null;
    }
}
