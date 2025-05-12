import bagel.*;

public class Bullet extends GameObject {
    private boolean isDestroyed = false;
    private boolean isRight = false;
    private final static Image LEFT_SPRITE = new Image("res/bullet_left.png");
    private final static Image RIGHT_SPRITE = new Image("res/bullet_right.png");
    private final static int BULLET_GRAVITY = 0;
    private final static int BULLET_TERMINAL_VELOCITY = 0;
    private final static double MOVEMENT_VELOCITY = 3.8;
    private final static int offscreen = -100;

    public Bullet(double centreX, double centreY, boolean isRight) {
        super(centreX, centreY, isRight ? RIGHT_SPRITE : LEFT_SPRITE, BULLET_GRAVITY, BULLET_TERMINAL_VELOCITY);
        this.isRight = isRight;
    }

    public boolean isDestroyed() {
        return this.isDestroyed;
    }

    private void destroy() {
        this.isDestroyed = true;
        setCentreY(offscreen);
        setCentreX(offscreen);
    }

    @Override
    public void display() {
        if (!isDestroyed) {
            getSprite().draw(getCentreX(), getCentreY());
        }
    }

    private void monkeyCollision(NormalMonkey[] normalMonkeys, IntelligentMonkey[] intelligentMonkeys, DonkeyKong donkeyKong) {
        for (NormalMonkey monkey : normalMonkeys) {
            if (isTouching(monkey)) {
                monkey.destroy();
                destroy();
            }
        }

        for (IntelligentMonkey monkey : intelligentMonkeys) {
            if (isTouching(monkey)) {
                monkey.destroy();
                destroy();
            }
        }

        if (isTouching(donkeyKong)) {
            donkeyKong.hit();
            destroy();
        }
    }

    public void update(NormalMonkey[] normalMonkeys, IntelligentMonkey[] intelligentMonkeys, DonkeyKong donkeyKong) {
        if (isRight) {
            setCentreX(getCentreX() + MOVEMENT_VELOCITY);
        } else {
            setCentreX(getCentreX() - MOVEMENT_VELOCITY);
        }
        if (getCentreX() >= ShadowDonkeyKong.getScreenWidth() || getCentreX() <= 0) {
            isDestroyed = true;
        }
        monkeyCollision(normalMonkeys, intelligentMonkeys, donkeyKong);
        display();
    }
}