import bagel.*;
import bagel.util.*;

public abstract class GameObject implements Collidable, Fallable {
    private double centreX, centreY;
    private double height, width;
    private Image sprite;
    private double velocityY = 0;
    private final double TERMINAL_VELOCITY;
    private final double GRAVITY;

    public GameObject(double centreX, double centreY, Image sprite, double gravity, double terminalVelocity) {
        this.centreX = centreX;
        this.centreY = centreY;
        this.width = sprite.getWidth();
        this.height = sprite.getHeight();
        this.sprite = sprite;
        this.TERMINAL_VELOCITY = terminalVelocity;
        this.GRAVITY = gravity;
    }

    public double getCentreX() {
        return this.centreX;
    }

    public void setCentreX(double centreX) {
        this.centreX = centreX;
    }

    public double getCentreY() {
        return this.centreY;
    }

    public void setCentreY(double centreY) {
        this.centreY = centreY;
    }

    public double getWidth() {
        return this.width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return this.height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getLeftX() {
        return this.centreX - (this.width / 2);
    }

    public double getRightX() {
        return this.centreX + (this.width / 2);
    }

    public double getTopY() {
        return this.centreY - (this.height / 2);
    }

    public double getBottomY() {
        return this.centreY + (this.height / 2);
    }

    public Image getSprite() {
        return this.sprite;
    }

    public void setSprite(Image sprite) {
        this.sprite = sprite;
        this.width = this.sprite.getWidth();
        this.height = this.sprite.getHeight();
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public double getVelocityY() {
        return this.velocityY;
    }

    public Rectangle getBoundingBox() {
        return sprite.getBoundingBoxAt(new Point(centreX, centreY));
    }

    public void display() {
        sprite.draw(centreX, centreY);
    }

    public boolean isTouching(GameObject object) {
        return getBoundingBox().intersects(object.getBoundingBox());
    }

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
