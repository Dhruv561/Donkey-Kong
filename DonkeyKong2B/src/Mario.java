import bagel.*;
import bagel.util.Rectangle;

public class Mario extends Entity implements Attackable {
    private static final Image RIGHT_SPRITE = new Image("res/mario_right.png");
    private static final Image LEFT_SPRITE = new Image("res/mario_left.png");;
    private static final Image RIGHT_HAMMER_SPRITE = new Image("res/mario_hammer_right.png");
    private static final Image LEFT_HAMMER_SPRITE = new Image("res/mario_hammer_left.png");
    private static final Image RIGHT_BLASTER_SPRITE = new Image("res/mario_blaster_right.png");;
    private static final Image LEFT_BLASTER_SPRITE = new Image("res/mario_blaster_left.png");;

    private boolean hasHammer = false;
    private boolean hasBlaster = false;
    private boolean isJumping = false;
    private int bulletsRemaining = 0;

    private static final int BULLETS = 5;
    private static final double MOVE_VELOCITY = 3.5;
    private static final double LADDER_VELOCITY = 2;
    private static final double JUMP_VELOCITY = -5;
    private static final double MARIO_TERMINAL_VELOCITY = 10;
    private static final double MARIO_GRAVITY = 0.2;

    public Mario(double centreX, double centreY) {
        super(centreX, centreY, LEFT_SPRITE, RIGHT_SPRITE, MARIO_GRAVITY, MARIO_TERMINAL_VELOCITY);
    }

    public void setActiveSprite() {
        if (isRight() && !hasHammer && !hasBlaster) {
            setSprite(RIGHT_SPRITE);
        } else if (!isRight() && !hasHammer && !hasBlaster) {
            setSprite(LEFT_SPRITE);
        } else if (isRight() && hasHammer && !hasBlaster) {
            setSprite(RIGHT_HAMMER_SPRITE);
        } else if (!isRight() && hasHammer && !hasBlaster) {
            setSprite(LEFT_HAMMER_SPRITE);
        } else if (isRight() && !hasHammer && hasBlaster) {
            setSprite(RIGHT_BLASTER_SPRITE);
        } else {
            setSprite(LEFT_BLASTER_SPRITE);
        }
    }

    public void touchingHammer(Hammer hammer) {
        if (isTouching(hammer)) {
            this.hasHammer = true;
            hammer.isCollected(true);
        }
    }

    @Override
    public void moveHorizontal() {}

    public void moveHorizontal(Input input) {
        if (input.isDown(Keys.LEFT)) {
            setCentreX(getCentreX() - MOVE_VELOCITY);
            setRight(false);
        } else if (input.isDown(Keys.RIGHT)) {
            setCentreX(getCentreX() + MOVE_VELOCITY);
            setRight(true);
        }
    }

    private void jump(boolean onPlatform, boolean wantsToJump) {
        if (onPlatform && wantsToJump) {
            setVelocityY(JUMP_VELOCITY);
            isJumping = true;
        }
        if (getBottomY() > ShadowDonkeyKong.getScreenHeight()) {
            setCentreY(ShadowDonkeyKong.getScreenHeight() - (getHeight() / 2));
            setVelocityY(0);
            isJumping = false;
        }
    }


    public boolean climbLadder(Input input, Ladder[] ladders) {
        boolean isOnLadder = false;
        for (Ladder ladder : ladders) {
            if (isTouching(ladder)) {
                // Check horizontal overlap so Mario is truly on the ladder
                if (ladder.getLeftX() <= getCentreX() && getCentreX() <= ladder.getRightX()) {
                    isOnLadder = true;

                    // Stop Mario from sliding up when not moving
                    if (!input.isDown(Keys.UP) && !input.isDown(Keys.DOWN)) {
                        setVelocityY(0);  // Prevent sliding inertia effect
                    }

                    // ----------- Climb UP -----------
                    if (input.isDown(Keys.UP)) {
                        setCentreY(getCentreY() - LADDER_VELOCITY);
                        setVelocityY(0);
                    }

                    // ----------- Climb DOWN -----------
                    if (input.isDown(Keys.DOWN)) {
                        // Calculate the next Y position after moving down
                        double nextY = getCentreY() + LADDER_VELOCITY;
                        // Calculate where the bottom of the player will be after moving
                        double nextBottomY = nextY + (getBottomY() - getCentreY());

                        // Check if we're still within the ladder's bounds after moving
                        if (nextBottomY <= ladder.getBottomY()) {
                            // Safe to move down
                            setCentreY(nextY);
                            setVelocityY(0);
                        } else {
                            // Mario has moved past the bottom of ladder nad needs to be realigned
                            double adjustedY = getCentreY() + (ladder.getBottomY() - getBottomY());
                            setCentreY(adjustedY);
                            setVelocityY(0);
                        }
                    }
                }
            } else if (getBottomY() == ladder.getTopY() && input.isDown(Keys.DOWN) &&
                    (ladder.getLeftX() <= getCentreX() && getCentreX() <= ladder.getRightX())) {
                // We're at the top of the ladder and pressing DOWN, so start climbing down
                double nextY = getCentreY() + LADDER_VELOCITY;
                setCentreY(nextY);
                setVelocityY(0); // ignore gravity
                isOnLadder = true; // Mark as on ladder to ignore gravity effects
            }
        }
        return isOnLadder;
    }

    private boolean fallToPlatform(Platform[] platforms, Hammer hammer) {
        boolean onPlatform = false;

        // We'll only snap Mario to a platform if he's moving downward (velocityY >= 0)
        // so we don't kill his jump in mid-air.
        if (getVelocityY() >= 0) {
            for (Platform platform : platforms) {
                Rectangle marioBounds    = getBoundingBox();
                Rectangle platformBounds = platform.getBoundingBox();

                if (marioBounds.intersects(platformBounds)) {
                    double marioBottom = marioBounds.bottom();
                    double platformTop = platformBounds.top();

                    // If Mario's bottom is at or above the platform's top
                    // and not far below it (a small threshold based on velocity)
                    if (marioBottom <= platformTop + getVelocityY()) {
                        // Snap Mario so his bottom = the platform top
                        setCentreY(platformTop - (getHeight() / 2));
                        setVelocityY(0);
                        isJumping = false;
                        onPlatform = true;
                        break; // We found a platform collision
                    }
                }
            }
        }
        return onPlatform;
    }

    @Override
    public void launchProjectile(GameObject projectile) {

    }

//    @Override
//    public void display() {
//        if (isRight() && !hasHammer) {
//            // facing right and has no hammer
//            RIGHT_SPRITE.draw(getCentreX(), getCentreY());
//        } else if (isRight() && hasHammer) {
//            // facing right and has hammer
//            RIGHT_HAMMER_SPRITE.draw(getCentreX(), getCentreY());
//        } else if (!isRight() && !hasHammer) {
//            // left right and has no hammer
//            LEFT_SPRITE.draw(getCentreX(), getCentreY());
//        } else {
//            // facing left and has hammer
//            LEFT_HAMMER_SPRITE.draw(getCentreX(), getCentreY());
//        }
//    }

    public void update(Input input, Platform[] platforms, Ladder[] ladders, Hammer hammer) {
        moveHorizontal(input); // 1) Horizontal movement
        //updateSprite(hammer); // 2) Update Mario’s current sprite (hammer or not, facing left or right)
        touchingHammer(hammer); // 3) If you just picked up the hammer:
        //updateSprite(); // 4) Now replace sprite (since either isFacingRight or hasHammer could have changed)

        // 5) Ladder logic – check if on a ladder
        boolean isOnLadder;
        isOnLadder = climbLadder(input, ladders);

        // 6) Jump logic: if on platform (we'll detect after we move) but let's queue jump if needed
        boolean wantsToJump = input.wasPressed(Keys.SPACE);

        // 7) If not on ladder, apply gravity, move Mario
        if (!isOnLadder) {
            setVelocityY(getVelocityY() + MARIO_GRAVITY);
            setVelocityY(Math.min(MARIO_TERMINAL_VELOCITY, getVelocityY()));
        }

        // 8) Actually move Mario vertically after gravity
        setCentreY(getCentreY() + getVelocityY());

        // 9) Check for platform collision AFTER Mario moves
        boolean onPlatform;
        onPlatform = fallToPlatform(platforms, hammer);

        // 10) If we are on the platform, allow jumping; Prevent Mario from falling below the ground
        jump(onPlatform, wantsToJump);

        // 11) Enforce horizontal screen bounds
        enforceBoundaries();

        // 12) Draw Mario
        setActiveSprite();
        display();
    }
}
