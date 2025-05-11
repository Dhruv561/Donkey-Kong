import bagel.*;

public class IntelligentMonkey extends Entity implements Attackable {
    private boolean isDestroyed = false;
    private int[] movementPattern;
    private final static double MOVEMENT_VELOCITY = 0.5;
    private final static int PROJECTILE_TIME = 5;
    private final static double INTELLIGENT_GRAVITY = 0.4;
    private final static double INTELLIGENT_TERMINAL_VELOCITY = 5;

    public IntelligentMonkey(double centreX, double centreY) {
        super(centreX, centreY, new Image("res/intelli_monkey_right.png"), new Image("res/intelli_monkey_left.png"), INTELLIGENT_GRAVITY, INTELLIGENT_TERMINAL_VELOCITY);
    }

    @Override
    public void moveLeft() {

    }

    @Override
    public void moveRight() {

    }

    @Override
    public void launchProjectile(GameObject projectile) {

    }


    public void update() {

    }
}
