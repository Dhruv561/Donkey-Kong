import bagel.*;

public class Mario extends Entity {
    private static final Image RIGHT_SPRITE = new Image("res/mario_right.png");
    private static final Image LEFT_SPRITE = new Image("res/mario_left.png");;
    private static final Image RIGHT_HAMMER_SPRITE = new Image("res/mario_hammer_right.png");
    private static final Image LEFT_HAMMER_SPRITE = new Image("res/mario_hammer_left.png");
    private static final Image RIGHT_BLASTER_SPRITE = new Image("res/mario_blaster_right.png");;
    private static final Image LEFT_BLASTER_SPRITE = new Image("res/mario_blaster_left.png");;

    private boolean hasHammer = false;
    private boolean hasBlaster = false;
    private int bulletsRemaining = 0;

    private static final int BULLETS = 5;
    private static final double MOVE_VELOCITY = 3.5;
    private static final double LADDER_VELOCITY = 2;
    private static final double JUMP_VELOCITY = 0;
    private static final double TERMINAL_VELOCITY = 10;
    private static final double GRAVITY = 0.2;

    public Mario(double centreX, double centreY) {
        super(centreX, centreY, RIGHT_SPRITE, LEFT_SPRITE, GRAVITY, TERMINAL_VELOCITY);
    }

    public void setActiveSprite() {
        if (isRight() && !hasHammer && !hasBlaster) {
            setSprite(RIGHT_SPRITE);
        } else if (!isRight() && !hasHammer && !hasBlaster) {
            setSprite(LEFT_SPRITE);
        } else if (isRight() && hasHammer && !hasBlaster) {
            setSprite(RIGHT_HAMMER_SPRITE);
        } else if (!isRight() && hasHammer && !hasBlaster) {
            setSprite(LEFT_HAMMER_SPRITE);
        } else if (isRight() && !hasHammer && hasBlaster) {
            setSprite(RIGHT_BLASTER_SPRITE);
        } else {
            setSprite(LEFT_BLASTER_SPRITE);
        }
    }

    @Override
    public void moveLeft() {

    }

    @Override
    public void moveRight() {

    }

    public void jump() {

    }

    public void climbUpLadder() {

    }

    public void climbDownLadder() {

    }

    public void launchProjectile() {

    }

    public void update() {

    }
}
