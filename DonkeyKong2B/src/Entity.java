import bagel.*;
import bagel.util.*;

public abstract class Entity extends GameObject implements Movable {
    private final Image RIGHT_SPRITE;
    private final Image LEFT_SPRITE;
    private boolean isRight = true;

    public Entity(double centreX, double centerY, Image leftImage, Image rightImage, double gravity, double terminalVelocity) {
        super(centreX, centerY, rightImage, gravity, terminalVelocity);
        this.RIGHT_SPRITE = rightImage;
        this.LEFT_SPRITE = leftImage;
    }

    public abstract void moveLeft();
    public abstract void moveRight();
    public void enforceBoundaries() {
        if (getLeftX() < 0) {
            setCentreX(getWidth() / 2);
        }
        if (getRightX() > ShadowDonkeyKong.getScreenWidth()) {
            setCentreX(ShadowDonkeyKong.getScreenWidth() - getWidth() / 2);
        }
        if (getTopY() < 0) {
            setCentreY(getWidth() / 2);
        }
        if (getBottomY() > ShadowDonkeyKong.getScreenHeight()) {
            setCentreY(ShadowDonkeyKong.getScreenHeight() - getHeight() / 2);
        }
    }

    @Override
    public void display() {
        if (isRight) {
            RIGHT_SPRITE.draw(getCentreX(), getCentreY());
        } else {
            LEFT_SPRITE.draw(getCentreX(), getCentreY());
        }
    }
}
