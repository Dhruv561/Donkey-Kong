import bagel.*;

public class IntelligentMonkey extends Entity implements Attackable {
    private boolean isDestroyed = false;
    private int[] movementPattern;
    private final static double MOVEMENT_VELOCITY = 0.5;
    private final static int PROJECTILE_TIME = 5;
    private final static double INTELLIGENT_GRAVITY = 0.4;
    private final static double INTELLIGENT_TERMINAL_VELOCITY = 5;
    private final static int offscreen = -100;

    public IntelligentMonkey(double centreX, double centreY, boolean isRight, int[] movementPattern) {
        super(centreX, centreY, new Image("res/intelli_monkey_right.png"), new Image("res/intelli_monkey_left.png"), INTELLIGENT_GRAVITY, INTELLIGENT_TERMINAL_VELOCITY);
        setRight(isRight);
        this.movementPattern = movementPattern;
    }

    public void destroy() {
        this.isDestroyed = true;
        setCentreX(offscreen);
        setCentreY(offscreen);
    }

    @Override
    public void display() {
        if (!isDestroyed) {
            getSprite().draw(getCentreX(), getCentreY());
        }
    }


    @Override
    public void moveHorizontal() {

    }

    @Override
    public void launchProjectile() {

    }

    public void update(Platform[] platforms) {
        fallToPlatform(platforms);
        launchProjectile();
        display();
    }
}
