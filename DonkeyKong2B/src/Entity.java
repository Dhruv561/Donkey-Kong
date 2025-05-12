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

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
        if (isRight) {
            setSprite(RIGHT_SPRITE);
        } else {
            setSprite(LEFT_SPRITE);
        }
    }

    public abstract void moveHorizontal();

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
}
