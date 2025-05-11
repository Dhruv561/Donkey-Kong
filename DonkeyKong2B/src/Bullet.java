import bagel.*;

public class Bullet extends GameObject {
    private boolean isDestroyed = false;
    private final static Image LEFT_SPRITE = new Image("res/bullet_left.png");
    private final static Image RIGHT_SPRITE = new Image("res/bullet_right.png");
    private final static int BULLET_GRAVITY = 0;
    private final static int BULLET_TERMINAL_VELOCITY = 0;
    private final static double MOVEMENT_VELOCITY = 3.8;

    public Bullet(double centreX, double centreY) {
        super(centreX, centreY, RIGHT_SPRITE, BULLET_GRAVITY, BULLET_TERMINAL_VELOCITY);
    }


    public void update() {

    }
}
