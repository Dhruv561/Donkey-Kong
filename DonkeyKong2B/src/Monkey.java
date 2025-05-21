import bagel.Image;

/**
 * This class {@code Entity} and is responsible for initialising,
 * rendering, movement and entity interaction for all monkeys.
 */
public class Monkey extends Entity {
    private int[] movementPattern; // movement array of pixels
    private int currentIdx = 0; // current position in movement array
    private double startingX; // starting position when moving in movement pattern
    private final static double MONKEY_GRAVITY = 0.4;
    private final static double MONKEY_TERMINAL_VELOCITY = 5;
    private final static double MOVEMENT_VELOCITY = 0.5;
    private final static Image normalMonkeyLeftImage =  new Image("res/normal_monkey_left.png");
    private final static Image normalMonkeyRightImage =  new Image("res/normal_monkey_right.png");

    /**
     * Initialises monkey based off position coordinates, left and right facing sprite (specifically for intelligent
     * monkeys), direction and movement pattern.
     * @param centreX centre x coordinates
     * @param centerY centre y coordinates
     * @param leftImage left facing sprite
     * @param rightImage right facing sprite
     * @param isRight is facing right direction
     * @param movementPattern array of pixels the monkey must move on each turn
     */
    public Monkey(double centreX, double centerY, Image leftImage, Image rightImage, boolean isRight,
                  int[] movementPattern) {
        super(centreX, centerY, leftImage, rightImage, MONKEY_GRAVITY, MONKEY_TERMINAL_VELOCITY);
        setRight(isRight);
        updateSprite();
        this.movementPattern = movementPattern;
        this.startingX = getCentreX();
    }

    /**
     * Initialises monkey based off position coordinates, left and right facing sprite of normal monkeys,
     * direction and movement pattern.
     * @param centreX centre x coordinates
     * @param centerY centre y coordinates
     * @param isRight is facing right direction
     * @param movementPattern array of pixels the monkey must move on each turn
     */
    public Monkey(double centreX, double centerY, boolean isRight,
                  int[] movementPattern) {
        super(centreX, centerY, normalMonkeyLeftImage, normalMonkeyRightImage, MONKEY_GRAVITY,
                MONKEY_TERMINAL_VELOCITY);
        setRight(isRight);
        updateSprite();
        this.movementPattern = movementPattern;
        this.startingX = getCentreX();
    }

    /**
     * Empty implementation for left and right movement from abstract method in Entity
     */
    @Override
    public void moveHorizontal() {}

    /**
     * Moves monkeys left and right based of the movement patterns. Ensures monkey
     * does not walk off the boundary of the window or the platform
     * @param platforms array of all platforms
     */
    public void moveHorizontal(Platform[] platforms) {
        if (isDestroyed()) {
            return;
        }

        // checks to see if monkey has moved the current distance in the movement pattern
        // checks to see if monkey has reached window or platform edges
        if (Math.abs(startingX - getCentreX()) >= movementPattern[currentIdx] || atBoundary(platforms)) {
            // updates the current index to cycle to the next movement pattern
            currentIdx = (currentIdx + 1) % movementPattern.length;
            startingX = getCentreX(); // reset starting coordinate for new movement
            setRight(!isRight()); // change directions
        }

        if (isRight()) {
            // move right
            setCentreX(getCentreX() + MOVEMENT_VELOCITY);
        } else {
            // move left
            setCentreX(getCentreX() - MOVEMENT_VELOCITY);
        }
    }

    /**
     * Update method which is used to make monkey fall to platform, move and render to screen
     * @param platforms array of all platforms
     */
    public void update(Platform[] platforms) {
        fallToPlatform(platforms);
        moveHorizontal(platforms); // move horizontally based off movement pattern
        updateSprite(); // update sprite for direction changes
        display();
    }
}
