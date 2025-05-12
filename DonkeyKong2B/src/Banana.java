import bagel.*;

public class Banana extends GameObject {
    private boolean isDestroyed = false;
    private boolean isRight;
    private final double startingX;
    private final static int BANANA_GRAVITY = 0;
    private final static int BANANA_TERMINAL_VELOCITY = 0;
    private final static double MOVEMENT_VELOCITY = 1.8;
    private final static int MAXIMUM_PIXELS = 300;
    private final static int offscreen = -100;

    public Banana(double centreX, double centreY, boolean isRight) {
        super(centreX, centreY, new Image("res/banana.png"), BANANA_GRAVITY, BANANA_TERMINAL_VELOCITY);
        this.isRight = isRight;
        this.startingX = centreX;
    }

    public void destroy() {
        this.isDestroyed = true;
        setCentreX(offscreen);
        setCentreY(offscreen);
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


    public void update(Mario mario) {
        if (isDestroyed) {
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
