import bagel.*;
import bagel.util.*;

/**
 * Parent abstract class for all game objects including mario,
 * donkey kong, ladders, hammer, platforms and barrels. Initialises
 * coordinates and dimensions for each entity, and handles gravity
 * interaction, platform collision interaction and sprite switching
 */
public abstract class GameObject {
    private double centreX, centreY;
    private double height, width;
    private Image sprite;
    private boolean isDestroyed = false;
    private double velocityY = 0;
    private final double TERMINAL_VELOCITY;
    private final double GRAVITY;
    private final static int OFFSCREEN = -1000;

    /**
     * Initialises game objects position coordinates, sprite and gravity values
     * @param centreX centre x coordinate
     * @param centreY centre y coordinate
     * @param sprite image for sprite
     * @param gravity acceleration from gravity
     * @param terminalVelocity maximum velocity
     */
    public GameObject(double centreX, double centreY, Image sprite, double gravity, double terminalVelocity) {
        this.centreX = centreX;
        this.centreY = centreY;
        this.width = sprite.getWidth();
        this.height = sprite.getHeight();
        this.sprite = sprite;
        this.TERMINAL_VELOCITY = terminalVelocity;
        this.GRAVITY = gravity;
    }

    /**
     * Returns centre x coordinate
     * @return centre x coordinate
     */
    public double getCentreX() {
        return this.centreX;
    }

    /**
     * Sets centre x coordinate
     * @param centreX centre x coordinate
     */
    public void setCentreX(double centreX) {
        this.centreX = centreX;
    }

    /**
     * Returns centre y coordinate
     * @return centre y coordinate
     */
    public double getCentreY() {
        return this.centreY;
    }

    /**
     * Sets centre y coordinate
     * @param centreY centre y coordiante
     */
    public void setCentreY(double centreY) {
        this.centreY = centreY;
    }

    /**
     * Returns object width
     * @return object width
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Sets object width
     * @param width object width
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Returns object height
     * @return object height
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Sets object height
     * @param height object height
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Returns left x coordinate of sprite
     * @return left x coordinate
     */
    public double getLeftX() {
        return this.centreX - (this.width / 2);
    }

    /**
     * Returns right x coordinate of sprite
     * @return right x coordinate
     */
    public double getRightX() {
        return this.centreX + (this.width / 2);
    }

    /**
     * Returns top y coordinate of sprite
     * @return top y coordinate
     */
    public double getTopY() {
        return this.centreY - (this.height / 2);
    }

    /**
     * Returns bottom y coordinate of sprite
     * @return bottom y coordinate
     */
    public double getBottomY() {
        return this.centreY + (this.height / 2);
    }

    /**
     * Returns current active sprite
     * @return current active sprite
     */
    public Image getSprite() {
        return this.sprite;
    }

    /**
     * Changes the active sprite and resizes sprite width and height
     * @param sprite new active sprite
     */
    public void setSprite(Image sprite) {
        this.sprite = sprite;
        this.width = sprite.getWidth();
        this.height = sprite.getHeight();
    }

    /**
     * Sets current vertical velocity
     * @param velocityY current vertical velocity
     */
    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    /**
     * Gets current vertical velocity
     * @return current vertical velocity
     */
    public double getVelocityY() {
        return this.velocityY;
    }

    /**
     * Destroys game object and moves it offscreen
     */
    public void destroy() {
        this.isDestroyed = true;
        setCentreX(OFFSCREEN);
        setCentreY(OFFSCREEN);
    }

    /**
     * Returns if sprite has been destroyed
     * @return boolean if destroyed
     */
    public boolean isDestroyed() {
        return this.isDestroyed;
    }

    /**
     * Returns bounding box or hit box of game object
     * @return Rectangle bounding box
     */
    public Rectangle getBoundingBox() {
        return sprite.getBoundingBoxAt(new Point(centreX, centreY));
    }

    /**
     * Draws game object from coordinates if not destroyed
     */
    public void display() {
        if (!isDestroyed) {
            sprite.draw(centreX, centreY);
        }
    }

    /**
     * Checks if another entity is intersecting an entity's bounding or hit box
     * @param object object which we want to check if intersecting
     * @return boolean whether the objects intersect
     */
    public boolean isTouching(GameObject object) {
        return getBoundingBox().intersects(object.getBoundingBox());
    }

    /**
     * Applies gravity to object and makes it fall down until it reaches
     * a platform after which it stops falling.
     * @param platforms array of all platforms
     */
    public void fallToPlatform(Platform[] platforms) {
        // 1) Apply gravity
        velocityY += GRAVITY;

        // 2) Limit falling speed to terminal velocity
        if (velocityY > TERMINAL_VELOCITY) {
            velocityY = TERMINAL_VELOCITY;
        }

        // 3) Move the object downward
        centreY += velocityY;

        // 4) Check for collision with platforms
        for (Platform platform : platforms) {
            if (isTouching(platform)) {
                // Position the object on top of the platform
                centreY = platform.getTopY() - (height / 2);
                velocityY = 0; // Stop falling
                break; // Stop checking further once the object lands
            }
        }
    }
}
