import bagel.*;
import java.util.*;

public class IntelligentMonkey extends Entity implements Attackable {
    private boolean isDestroyed = false;
    private int currentFrame = 5;
    private int lastShot = 0;
    private int[] movementPattern;
    private ArrayList<Banana> bananas = new ArrayList<Banana>();
    private final static double MOVEMENT_VELOCITY = 0.5;
    private final static int PROJECTILE_COOLDOWN = 5;
    private final static double INTELLIGENT_GRAVITY = 0.4;
    private final static double INTELLIGENT_TERMINAL_VELOCITY = 5;
    private final static int offscreen = -100;

    public IntelligentMonkey(double centreX, double centreY, boolean isRight, int[] movementPattern) {
        super(centreX, centreY, new Image("res/intelli_monkey_left.png"), new Image("res/intelli_monkey_right.png"), INTELLIGENT_GRAVITY, INTELLIGENT_TERMINAL_VELOCITY);
        setRight(isRight);
        this.movementPattern = movementPattern;
    }

    public void destroy() {
        this.isDestroyed = true;
        setCentreX(offscreen);
        setCentreY(offscreen);

//        for (Banana banana : bananas) {
//            destroy();
//        }
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
