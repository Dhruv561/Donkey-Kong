import bagel.*;

public class NormalMonkey extends Entity {
    private boolean isDestroyed = false;
    private int[] movementPattern;
    private final static double MOVEMENT_VELOCITY = 0.5;
    private final static double NORMAL_GRAVITY = 0.4;
    private final static double NORMAL_TERMINAL_VELOCITY = 5;
    private final static int offscreen = -100;

    public NormalMonkey(double centreX, double centreY, boolean isRight, int[] movementPattern) {
        super(centreX, centreY, new Image("res/normal_monkey_right.png"), new Image("res/normal_monkey_left.png"), NORMAL_GRAVITY, NORMAL_TERMINAL_VELOCITY);
        setRight(isRight);
        this.movementPattern = movementPattern;
    }

    public void destroy() {
        this.isDestroyed = true;
        setCentreX(offscreen);
        setCentreY(offscreen);
    }

    @Override
    public void moveHorizontal() {

    }

    public void update(Platform[] platforms) {
        fallToPlatform(platforms);
        display();
    }
}
