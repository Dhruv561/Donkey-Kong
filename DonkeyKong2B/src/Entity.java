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
    }

    public void updateSprite() {
        setSprite(isRight ? RIGHT_SPRITE : LEFT_SPRITE);
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

    public boolean atBoundary(Platform[] platforms) {
        boolean rightEdge = getRightX() >= ShadowDonkeyKong.getScreenWidth();
        boolean leftEdge = getLeftX() <= 0;

        Platform platform = findPlatform(platforms);
        boolean platformEdge = false;

        if (platform != null) {
            boolean leftPlatformEdge = getLeftX() <= platform.getLeftX();
            boolean rightPlatformEdge = getRightX() >= platform.getRightX();
            platformEdge = leftPlatformEdge || rightPlatformEdge;
        }

        return rightEdge || leftEdge || platformEdge;
    }

    public Platform findPlatform(Platform[] platforms) {
        for (Platform platform : platforms) {
            if (getBottomY() == platform.getTopY()) {
                return platform;
            }
        }
        return null;
    }
}
