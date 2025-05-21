import bagel.*;
import java.util.*;

/**
 * This class {@code Monkey} and is responsible for initialising,
 * rendering, movement and entity interaction for all intelligent monkeys.
 */
public class IntelligentMonkey extends Monkey implements Attackable {
    private boolean isDestroyed = false;
    private int currentFrame = 5; // current time
    private int lastShot = 0; // time at which last banana was thrown
    private ArrayList<Banana> bananas = new ArrayList<Banana>();
    private final static int PROJECTILE_COOLDOWN = 5; // cooldown period before throwing bananas
    private final static int OFFSCREEN = -1000; // pixel coordinates offscreen

    /**
     * Initialises intelligent monkey based off position coordinates, left and right facing sprite of normal monkeys,
     * direction and movement pattern.
     * @param centreX centre x coordinates
     * @param centreY centre y coordinates
     * @param isRight is facing right direction
     * @param movementPattern array of pixels the monkey must move on each turn
     */
    public IntelligentMonkey(double centreX, double centreY, boolean isRight, int[] movementPattern) {
        super(centreX, centreY, new Image("res/intelli_monkey_left.png"),
                new Image("res/intelli_monkey_right.png"), isRight, movementPattern);
    }

    /**
     * Destroys the intelligent monkey, moves it offscreen and also destroys all bananas associated
     * assosicated with the intelligent monkey
     */
    @Override
    public void destroy() {
        // destroy and move monkey offscreen
        this.isDestroyed = true;
        setCentreX(OFFSCREEN);
        setCentreY(OFFSCREEN);
        //destroy each banana
        for (Banana banana: bananas) {
            banana.destroy();
        }
    }

    /**
     * Empty implementation of launch projectile method from Entity class
     */
    @Override
    public void launchProjectile() {}

    /**
     * Creates / launches banana from intelligent monkey based on cooldown period, adding
     * into an array list to track all bananas
     * @param stats game stats used to get remaining time
     */
    public void launchProjectile(GameStats stats) {
        if (stats.getRemainingTime(lastShot) - stats.getRemainingTime(currentFrame) >= PROJECTILE_COOLDOWN) {
            // fire banana if time between banana throws exceeds cooldown
            Banana banana = new Banana(getCentreX(), getCentreY(), isRight()); // create new banana
            bananas.add(banana);
            lastShot = currentFrame; // update time which last banana was thrown
        }
    }

    /**
     * Update method which is used to make intelligent monkeys fall to platform, move and render to screen.
     * Also handles banana launching and destroys all bananas if monkey is destroyed.
     * @param platforms array of platforms
     * @param mario mario
     * @param stats game stats
     */
    public void update(Platform[] platforms, Mario mario, GameStats stats) {
        fallToPlatform(platforms);
        moveHorizontal(platforms); // move horizontally based off movement pattern

        launchProjectile(stats); // create and handle banana launching
        for (Banana banana : bananas) {
            if (!banana.isDestroyed()) {
                banana.update(mario); // update state of every banana to handle movement
            }
        }

        updateSprite(); // update if direction has changed left or right
        display();
        currentFrame++; // increase current time
    }
}
