import bagel.*;

public class NormalMonkey extends Entity {
    private boolean isDestroyed = false;
    private int[] movementPattern;
    private int currentIdx = 0;
    private double startingX;
    private final static double MOVEMENT_VELOCITY = 0.5;
    private final static double NORMAL_GRAVITY = 0.4;
    private final static double NORMAL_TERMINAL_VELOCITY = 5;
    private final static int OFFSCREEN = -100;

    public NormalMonkey(double centreX, double centreY, boolean isRight, int[] movementPattern) {
        super(centreX, centreY, new Image("res/normal_monkey_left.png"),
                new Image("res/normal_monkey_right.png"), NORMAL_GRAVITY, NORMAL_TERMINAL_VELOCITY);
        setRight(isRight);
        updateSprite();
        this.movementPattern = movementPattern;
        this.startingX = getCentreX();
    }

    public void destroy() {
        this.isDestroyed = true;
        setCentreX(OFFSCREEN);
        setCentreY(OFFSCREEN);
    }

    @Override
    public void moveHorizontal() {}

    public void moveHorizontal(Platform[] platforms) {
        if (isDestroyed) {
            return;
        }

        if (Math.abs(startingX - getCentreX()) >= movementPattern[currentIdx] || atBoundary(platforms)) {
            currentIdx = (currentIdx + 1) % movementPattern.length;
            startingX = getCentreX();
            setRight(!isRight());
        }

        if (isRight()) {
            setCentreX(getCentreX() + MOVEMENT_VELOCITY);
        } else {
            setCentreX(getCentreX() - MOVEMENT_VELOCITY);
        }
    }

    public void update(Platform[] platforms) {
        fallToPlatform(platforms);
        moveHorizontal(platforms);
        updateSprite();
        display();
    }
}
