import bagel.*;
import java.util.*;

public class IntelligentMonkey extends Entity implements Attackable {
    private boolean isDestroyed = false;
    private int currentFrame = 5;
    private int lastShot = 0;
    private int[] movementPattern;
    private int currentIdx = 0;
    private double startingX;
    private ArrayList<Banana> bananas = new ArrayList<Banana>();
    private final static double MOVEMENT_VELOCITY = 0.5;
    private final static int PROJECTILE_COOLDOWN = 5;
    private final static double INTELLIGENT_GRAVITY = 0.4;
    private final static double INTELLIGENT_TERMINAL_VELOCITY = 5;
    private final static int OFFSCREEN = -100;

    public IntelligentMonkey(double centreX, double centreY, boolean isRight, int[] movementPattern) {
        super(centreX, centreY, new Image("res/intelli_monkey_left.png"),
                new Image("res/intelli_monkey_right.png"), INTELLIGENT_GRAVITY, INTELLIGENT_TERMINAL_VELOCITY);
        setRight(isRight);
        updateSprite();
        this.movementPattern = movementPattern;
        this.startingX = getCentreX();
    }

    public void destroy() {
        this.isDestroyed = true;
        setCentreX(OFFSCREEN);
        setCentreY(OFFSCREEN);
        for (Banana banana: bananas) {
            banana.destroy();
        }
    }

    @Override
    public void display() {
        if (!isDestroyed) {
            getSprite().draw(getCentreX(), getCentreY());
        }
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

    @Override
    public void launchProjectile() {}

    public void launchProjectile(GameStats stats) {
        if (stats.getRemainingTime(lastShot) - stats.getRemainingTime(currentFrame) >= PROJECTILE_COOLDOWN) {
            Banana banana = new Banana(getCentreX(), getCentreY(), isRight());
            bananas.add(banana);
            lastShot = currentFrame;
        }
    }

    public void update(Platform[] platforms, Mario mario, GameStats stats) {
        fallToPlatform(platforms);
        moveHorizontal(platforms);

        launchProjectile(stats);
        for (Banana banana : bananas) {
            if (!banana.isDestroyed()) {
                banana.update(mario);
            }
        }

        updateSprite();
        display();
        currentFrame++;
    }
}
