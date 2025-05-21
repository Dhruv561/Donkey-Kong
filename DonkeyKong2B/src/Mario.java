import bagel.*;
import bagel.util.Rectangle;
import java.util.*;

/**
 * This class {@code Entity} and is responsible for initialising,
 * rendering, movement and entity interaction for mario.
 */
public class Mario extends Entity implements Attackable {
    private static final Image RIGHT_SPRITE = new Image("res/mario_right.png");
    private static final Image LEFT_SPRITE = new Image("res/mario_left.png");
    private static final Image RIGHT_HAMMER_SPRITE = new Image("res/mario_hammer_right.png");
    private static final Image LEFT_HAMMER_SPRITE = new Image("res/mario_hammer_left.png");
    private static final Image RIGHT_BLASTER_SPRITE = new Image("res/mario_blaster_right.png");
    private static final Image LEFT_BLASTER_SPRITE = new Image("res/mario_blaster_left.png");

    private boolean hasHammer = false;
    private boolean hasBlaster = false;
    private boolean isJumping = false;
    private boolean isHit = false;
    private int bulletsRemaining = 0;
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();

    private static final int BULLETS = 5;
    private static final double MOVE_VELOCITY = 3.5;
    private static final double LADDER_VELOCITY = 2;
    private static final double FINAL_JUMP_VELOCITY = 0;
    private static final double INITIAL_JUMP_VELOCITY = -5;
    private static final double MARIO_TERMINAL_VELOCITY = 10;
    private static final double MARIO_GRAVITY = 0.2;

    /**
     * Initialises mario based off position coordinates, sprite and gravity values
     * @param centreX centre x coordinate
     * @param centreY centre y coordinate
     */
    public Mario(double centreX, double centreY) {
        super(centreX, centreY, LEFT_SPRITE, RIGHT_SPRITE, MARIO_GRAVITY, MARIO_TERMINAL_VELOCITY);
    }

    /**
     * updates sprite based off whether mario is facing left, right, has hammer or blaster.
     * Also updates the position coordinates to adjust for differences in width and height
     * between images
     */
    @Override
    protected void updateSprite() {
        // remember the old image and its bottom
        double oldBottom = getBottomY();

        // assign the new image based on direction facing, hammer and blaster
        if (hasHammer) {
            setSprite(isRight() ? RIGHT_HAMMER_SPRITE : LEFT_HAMMER_SPRITE);
        } else if (hasBlaster) {
            setSprite(isRight() ? RIGHT_BLASTER_SPRITE : LEFT_BLASTER_SPRITE);
        } else {
            setSprite(isRight() ? RIGHT_SPRITE : LEFT_SPRITE);
        }

        // shift 'y' so the bottom edge is the same as before
        // (if new sprite is taller, we move Mario up so he doesn't sink into platforms)
        setCentreY(getCentreY() - (getBottomY() - oldBottom));

        // update the recorded width/height to match the new image
        setWidth(getSprite().getWidth());
        setHeight(getHeight());
    }

    /**
     * Checks if mario is touching a hammer and if it is, reduces bullets to 0 and collects hammer
     * @param hammers array of all hammers
     */
    private void touchingHammer(Hammer[] hammers) {
        for (Hammer hammer : hammers) {
            // check if touching hammer
            if (isTouching(hammer)) {
                this.hasHammer = true; // grab hammer
                this.hasBlaster = false; // drop blaster
                this.bulletsRemaining = 0; // reset bullet count
                hammer.collect(); // collect hammer
            }
        }

    }

    /**
     * Checks if mario is touching a blaster and if it is, increases bullets and collects blaster
     * @param blasters array of blasters
     */
    private void touchingBlaster(Blaster[] blasters) {
        for (Blaster blaster : blasters) {
            // check if touching blaster
            if (isTouching(blaster)) {
                this.hasBlaster = true; // grab blaster
                this.hasHammer = false; // drop hammer
                blaster.collect(); // collect blaster
                bulletsRemaining += BULLETS; // increase bullets
            }
        }
    }

    /**
     * Returns if mario has hammer
     * @return boolean if mario has hammer
     */
    public boolean hasHammer() {
        return this.hasHammer;
    }

    /**
     * Sets condition as hit
     */
    public void hit() {
        this.isHit = true;
    }

    /**
     * Returns marios condition of whether he is hit or not
     * @return boolean if hit
     */
    public boolean isHit() {
        return this.isHit;
    }

    /**
     * Returns the number of bullets mario has
     * @return bullets remaining
     */
    public int getBullets() {
        return this.bulletsRemaining;
    }

    /**
     * Returns marios maximum possible jump height
     * @return maximum jump height
     */
    private double getMaxJumpHeight() {
        // calculates maximum jump velocity
        return Math.abs((Math.pow(FINAL_JUMP_VELOCITY, 2) - Math.pow(INITIAL_JUMP_VELOCITY, 2)) / (2 * MARIO_GRAVITY));
    }

    /**
     * Empty implementation of abstract move method in Entity class
     */
    @Override
    public void moveHorizontal() {}

    /**
     * Handles marios left and right movement based on user input whilst updating his position coordinates,
     * direction and sprite.
     * @param input user input
     */
    public void moveHorizontal(Input input) {
        if (input.isDown(Keys.LEFT)) {
            // user wants to move left
            setCentreX(getCentreX() - MOVE_VELOCITY); // move left
            setRight(false); // face left
            updateSprite(); // update sprite image
        } else if (input.isDown(Keys.RIGHT)) {
            // user wants to move right
            setCentreX(getCentreX() + MOVE_VELOCITY); // move right
            setRight(true); // face right
            updateSprite(); // update sprite image
        }
    }

    /**
     * Handles marios jumping movement. Allows mario to move if on platform and wanting to jump,
     * ensuring mario does not fall out of window dimensions.
     * @param onPlatform boolean if mario on platform
     * @param wantsToJump boolean if mario wants to jump
     */
    private void jump(boolean onPlatform, boolean wantsToJump) {
        // check if wants to jump and on platform
        if (onPlatform && wantsToJump) {
            setVelocityY(INITIAL_JUMP_VELOCITY); // jump upwards
            isJumping = true;
        }
        if (getBottomY() > ShadowDonkeyKong.getScreenHeight()) {
            // checks if mario has fallen through window bottom
            setCentreY(ShadowDonkeyKong.getScreenHeight() - (getHeight() / 2)); // reset coordinates to remain onscreen
            setVelocityY(0); // reset velocity to stop jumping
            isJumping = false; // stop jumping
        }
    }

    /**
     * Returns boolean if mario has successfully jumped over a barrel
     * @param barrel barrel object
     * @return boolean if mario has jumped over barrel
     */
    public boolean jumpingOverBarrel(Barrel barrel) {
            double jumpRange = barrel.getBottomY() - getMaxJumpHeight() - getHeight() / 2; // area above barrel
            // marios x coordinate is in between barrel
            boolean inlineWithBarrel = barrel.getLeftX() <= getCentreX() && getCentreX() <= barrel.getRightX();
            boolean overBarrel = getCentreY() < barrel.getTopY(); // marios y coordinate is over barrels
            boolean inJumpingRange = getCentreY() >= jumpRange; // mario is in jumping range above barrel

            return inlineWithBarrel && overBarrel && inJumpingRange; // mario jumping over barrel
    }

    /**
     * Handles ladder interaction with mario. Allows mario to climb up and down ladders based on
     * user input and whether mario is on the ladder. Ensures that mario is not able to climb above
     * or below ladders.
     * @param input user input
     * @param ladders array of all ladders
     * @return boolean if mario is on the ladder
     */
    private boolean climbLadder(Input input, Ladder[] ladders) {
        boolean isOnLadder = false;
        for (Ladder ladder : ladders) {
            if (isTouching(ladder)) {
                // check horizontal overlap so Mario is truly on the ladder
                if (ladder.getLeftX() <= getCentreX() && getCentreX() <= ladder.getRightX()) {
                    isOnLadder = true;

                    // stop Mario from sliding up when not moving
                    if (!input.isDown(Keys.UP) && !input.isDown(Keys.DOWN)) {
                        setVelocityY(0);  // Prevent sliding inertia effect
                    }

                    // climb up platform
                    if (input.isDown(Keys.UP)) {
                        climbUp();
                    }

                    // climb down platform
                    if (input.isDown(Keys.DOWN)) {
                        climbDown(ladder);
                    }
                }
            } else if (getBottomY() == ladder.getTopY() && input.isDown(Keys.DOWN) &&
                    (ladder.getLeftX() <= getCentreX() && getCentreX() <= ladder.getRightX())) {
                // mario at the top of the ladder and pressing DOWN, so start climbing down
                double nextY = getCentreY() + LADDER_VELOCITY;
                setCentreY(nextY);
                setVelocityY(0); // ignore gravity
                isOnLadder = true; // mark as on ladder to ignore gravity effects
            }
        }
        return isOnLadder;
    }

    /**
     * Method to make mario climb up a ladder by changing his velocity nad position coordinates
     */
    private void climbUp() {
        setCentreY(getCentreY() - LADDER_VELOCITY); // move up ladder
        setVelocityY(0); // set velocity to avoid falling off
    }

    /**
     * Method to make mario climb down a ladder by changing his velocity nad position coordinates.
     * Ensures mario is not able to move underneath the ladder based on his current velocity.
     * @param ladder ladder object
     */
    private void climbDown(Ladder ladder) {
        // calculate the next Y position after moving down
        double nextY = getCentreY() + LADDER_VELOCITY;
        // calculate where the bottom of the player will be after moving
        double nextBottomY = nextY + (getBottomY() - getCentreY());

        // check if we're still within the ladder's bounds after moving
        if (nextBottomY <= ladder.getBottomY()) {
            // safe to move down
            setCentreY(nextY);
            setVelocityY(0);
        } else {
            // mario has moved past the bottom of ladder nad needs to be realigned
            double adjustedY = getCentreY() + (ladder.getBottomY() - getBottomY());
            setCentreY(adjustedY);
            setVelocityY(0);
        }
    }

    /**
     * Applies gravity to mario and makes him fall downwards to the nearest platform beneath his feet.
     * @param platforms array of all platforms
     * @param hammers array of all hammers
     * @return boolean if mario is on the platform
     */
    private boolean fallToPlatform(Platform[] platforms, Hammer[] hammers) {
        boolean onPlatform = false;

        // only snap Mario to a platform if he's moving downward (velocityY >= 0) so we don't kill his jump in midair.
        if (getVelocityY() >= 0) {
            for (Platform platform : platforms) {
                Rectangle marioBounds    = getBoundingBox();
                Rectangle platformBounds = platform.getBoundingBox();

                if (marioBounds.intersects(platformBounds)) {
                    double marioBottom = marioBounds.bottom();
                    double platformTop = platformBounds.top();

                    // if Mario's bottom is at or above the platform's top
                    // and not far below it (a small threshold based on velocity)
                    if (marioBottom <= platformTop + getVelocityY()) {
                        // snap Mario so his bottom = the platform top
                        setCentreY(platformTop - (getHeight() / 2));
                        setVelocityY(0);
                        isJumping = false;
                        onPlatform = true;
                        break; // we found a platform collision
                    }
                }
            }
        }
        return onPlatform;
    }

    /**
     * Checks and returns boolean of whether mario is on a platform
     * @param platforms array of all platforms
     * @return boolean if mario is on platform
     */
    public boolean onPlatform(Platform[] platforms) {
        for (Platform platform : platforms) {
            // check if mario's bottom is on top of platform so mario is on platform
            if (platform.getTopY() == getBottomY()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Empty Implementation of method from Attackable interface
     */
    @Override
    public void launchProjectile() {}

    /**
     * Creates and adds bullet projectile to arraylist of bullets, allowing bullet to be launched based on user input.
     * Remaining bullets is reduced by once launched.
     * @param input user input
     */
    public void launchProjectile(Input input) {
        // check to see if we have bullets, blaster and user wants to shoot
        if (bulletsRemaining > 0 && input.wasPressed(Keys.S) && hasBlaster) {
            bulletsRemaining -= 1; // decrease bullets
            Bullet bullet = new Bullet(getCentreX(), getCentreY(), isRight()); // create new bullet
            bullets.add(bullet); // add to bullet array
        }
    }

    /**
     * Update method to handle marios horizontal movement, applying gravity and platform interaction,
     * jumping mechanics and hammer interaction.
     * @param input user input
     * @param platforms array of all platforms
     * @param ladders array of all ladders
     * @param hammers array of all hammers
     */
    public void update(Input input, Platform[] platforms, Ladder[] ladders, Hammer[] hammers) {
        moveHorizontal(input); // move left or right based on user input
        touchingHammer(hammers); // check if picked up hammer

        boolean isOnLadder;
        isOnLadder = climbLadder(input, ladders); // check if mario on ladder

        boolean wantsToJump = input.wasPressed(Keys.SPACE); // check if user wants to jump

        if (!isOnLadder) {
            setVelocityY(getVelocityY() + MARIO_GRAVITY); // apply gravity to avoid levitating
            setVelocityY(Math.min(MARIO_TERMINAL_VELOCITY, getVelocityY())); // ensure velocity doesn't exceed terminal
        }

        setCentreY(getCentreY() + getVelocityY()); // update y coordinate based on gravity

        boolean onPlatform;
        onPlatform = fallToPlatform(platforms, hammers); // check if mario is on platform

        jump(onPlatform, wantsToJump); // allow mario to jump

        enforceBoundaries(); // enforce mario to stay within game window

        updateSprite(); // update marios sprite based on direction and collectables
        display(); // display
    }

    /**
     * Update method to handle marios horizontal movement, applying gravity and platform interaction,
     * jumping mechanics, hammer interaction, bullet firing and interaction, and monkey interaction.
     * @param input user input
     * @param platforms array of all platforms
     * @param ladders array of all ladders
     * @param hammers array of all hammers
     * @param blaster array of all blasters
     * @param normalMonkeys array of all normal monkeys
     * @param intelligentMonkeys array of all intelligent monkeys
     * @param donkeyKong donkey kong object
     * @param stats game stats object
     */
    public void update(Input input, Platform[] platforms, Ladder[] ladders, Hammer[] hammers, Blaster[] blaster,
                       Monkey[] normalMonkeys, IntelligentMonkey[] intelligentMonkeys, DonkeyKong donkeyKong,
                       GameStats stats) {
        moveHorizontal(input);  // move left or right based on user input
        touchingHammer(hammers); // check if picked up hammer
        touchingBlaster(blaster); // check if picked up blaster
        launchProjectile(input); // launch bullets based on input

        for (Bullet bullet : bullets) {
            if (!bullet.isDestroyed()) {
                bullet.update(platforms, normalMonkeys, intelligentMonkeys, donkeyKong, stats); // update bullet status
            }
        }

        boolean isOnLadder;
        isOnLadder = climbLadder(input, ladders); // check if mario on ladder

        boolean wantsToJump = input.wasPressed(Keys.SPACE); // allow mario to jump

        if (!isOnLadder) {
            setVelocityY(getVelocityY() + MARIO_GRAVITY); // apply gravity to avoid levitating
            setVelocityY(Math.min(MARIO_TERMINAL_VELOCITY, getVelocityY())); // ensure velocity doesn't exceed terminal
        }

        setCentreY(getCentreY() + getVelocityY()); // update y coordinate based on gravity

        boolean onPlatform;
        onPlatform = fallToPlatform(platforms, hammers); // check if mario is on platform

        jump(onPlatform, wantsToJump); // allow mario to jump

        enforceBoundaries();  // enforce mario to stay within game window

        updateSprite(); // update marios sprite based on direction and collectables
        display(); // display
    }
}