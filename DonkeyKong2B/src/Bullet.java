import bagel.*;

public class Bullet extends GameObject {
    private boolean isDestroyed = false;
    private boolean isRight = false;
    private final static Image LEFT_SPRITE = new Image("res/bullet_left.png");
    private final static Image RIGHT_SPRITE = new Image("res/bullet_right.png");
    private final static int BULLET_GRAVITY = 0;
    private final static int BULLET_TERMINAL_VELOCITY = 0;
    private final static double MOVEMENT_VELOCITY = 3.8;

    public Bullet(double centreX, double centreY, boolean isRight) {
        super(centreX, centreY, isRight ? RIGHT_SPRITE : LEFT_SPRITE, BULLET_GRAVITY, BULLET_TERMINAL_VELOCITY);
        this.isRight = isRight;
    }

    public boolean isDestroyed() {
        return this.isDestroyed;
    }

    @Override
    public void display() {
        if (!isDestroyed) {
            getSprite().draw(getCentreX(), getCentreY());
        }
    }

    public void update() {
        if (isRight) {
            setCentreX(getCentreX() + MOVEMENT_VELOCITY);
        } else {
            setCentreX(getCentreX() - MOVEMENT_VELOCITY);
        }
        if (getCentreX() >= ShadowDonkeyKong.getScreenWidth() || getCentreX() <= 0) {
            isDestroyed = true;
        }
        display();
    }
}