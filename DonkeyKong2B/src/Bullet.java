import bagel.*;

/**
 * This class {@code GameObject} and is responsible for initialising,
 * rendering, moving and handling entity interaction for bananas.
 */
public class Bullet extends GameObject {
    private boolean isRight = false;
    private double startingX;
    private final static Image LEFT_SPRITE = new Image("res/bullet_left.png");
    private final static Image RIGHT_SPRITE = new Image("res/bullet_right.png");
    private final static int BULLET_GRAVITY = 0;
    private final static int BULLET_TERMINAL_VELOCITY = 0;
    private final static double MOVEMENT_VELOCITY = 3.8;
    private final static double MAXIMUM_PIXELS = 300;

    /**
     * Initialising bullet with position coordinates, direction, sprite and gravity values
     * @param centreX centre x coordinate
     * @param centreY centre y coordinate
     * @param isRight is facing right
     */
    public Bullet(double centreX, double centreY, boolean isRight) {
        super(centreX, centreY, isRight ? RIGHT_SPRITE : LEFT_SPRITE, BULLET_GRAVITY, BULLET_TERMINAL_VELOCITY);
        this.isRight = isRight;
        this.startingX = getCentreX();
    }

    /**
     * Checks for collision between both intelligent and normal monkeys. If there is
     * a collision, both the monkey and bullet are destroyed and points are updated.
     * If bullet hits donkey kong, his health is reduced and bullet is destroyed
     * @param normalMonkeys array of normal monkeys
     * @param intelligentMonkeys array of intelligent monkeys
     * @param donkeyKong donkey kong
     * @param stats game stats
     */
    private void monkeyCollision(Monkey[] normalMonkeys, IntelligentMonkey[] intelligentMonkeys,
                                 DonkeyKong donkeyKong, GameStats stats) {
        for (Monkey monkey : normalMonkeys) {
            if (isTouching(monkey)) {
                monkey.destroy();
                stats.monkeyDestroyed();
                destroy();
            }
        }

        for (IntelligentMonkey monkey : intelligentMonkeys) {
            if (isTouching(monkey)) {
                monkey.destroy();
                stats.monkeyDestroyed();
                destroy();
            }
        }

        if (isTouching(donkeyKong)) {
            donkeyKong.hit();
            destroy();
        }
    }

    /**
     * Updates and renders bullet position if not destroyed, moving left
     * or right based on its direction for at most MAXIMUM_PIXELS amount
     * before being auto destroyed. Also hands interaction with monkeys and platforms.
     * @param platforms array of platforms
     * @param normalMonkeys array of normal monkeys
     * @param intelligentMonkeys array of intelligent monkeys
     * @param donkeyKong donkey kong
     * @param stats game stats
     */
    public void update(Platform[] platforms, Monkey[] normalMonkeys, IntelligentMonkey[] intelligentMonkeys,
                       DonkeyKong donkeyKong, GameStats stats) {
        if (Math.abs(startingX - getCentreX()) >= MAXIMUM_PIXELS) {
            destroy();
        } else {
            if (isRight) {
                setCentreX(getCentreX() + MOVEMENT_VELOCITY);
            } else {
                setCentreX(getCentreX() - MOVEMENT_VELOCITY);
            }
        }
        if (getCentreX() >= ShadowDonkeyKong.getScreenWidth() || getCentreX() <= 0) {
            destroy();
        }
        for (Platform platform : platforms) {
            if (isTouching(platform)) {
                destroy();
            }
        }
        monkeyCollision(normalMonkeys, intelligentMonkeys, donkeyKong, stats);
        display();
    }
}