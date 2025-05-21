import java.util.Properties;
import bagel.*;

/**
 * This class extends {@code GameScreen} and is responsible for creating,
 * updating, rendering, handling and interaction of game objects.
 * It sets up the game world, initialises characters, platforms,
 * ladders, and other game objects, and runs the game loop. It
 * handles all game conditions and checks whether the level has
 * completed or failed.
 */
public class LevelTwo extends GameScreen {
    private final static int LEVEL = 2;

    /**
     * Initialises game stats and loads level
     * @param gameProps game properties
     * @param messageProps message properties
     */
    public LevelTwo(Properties gameProps, Properties messageProps, int points) {
        super(gameProps, messageProps);
        getStats().setPoints(points);
        loadLevel();
    }

    /**
     * Implementation method in Screen class which initialises all game objects for level 2
     * such as getMario(), platforms, barrels, and monkeys
     */
    @Override
    public void loadLevel() {
        // create mario at appropriate coordinates
        setMario(new Mario(Integer.parseInt(getGAME_PROPS().getProperty("mario.level2").split(",")[0]),
                Integer.parseInt(getGAME_PROPS().getProperty("mario.level2").split(",")[1])));
        // create donkey kong at appropriate coordinates
        setDonkeyKong(new DonkeyKong(Integer.parseInt(getGAME_PROPS().getProperty("donkey.level2").split(",")[0]),
                Integer.parseInt(getGAME_PROPS().getProperty("donkey.level2").split(",")[1])));
        createPlatforms(LEVEL);
        createBarrels(LEVEL);
        createLadders(LEVEL);
        createHammers(LEVEL);
        createBlasters();
        createMonkeys();
    }

    /**
     * Returns the current points earnt
     * @return current points
     */
    public int getPoints() {
        return getStats().getPoints();
    }

    /**
     * Checks the game state and whether the game is still running
     * or the game ending condition has been met. If game is over,
     * changes boolean gameWon to true or false depending on if game
     * has been won or lost. Also updates points from object interactions
     * if required.
     */
    @Override
    public void checkGameStatus() {
        // mario is touching donkey kong
        if (getMario().isTouching(getDonkeyKong())) {
            if (getMario().hasHammer()) {
                // mario touches donkey kong with hammer
                setGameWon(true); // game is won
                getStats().timeMultiplier(); // time multiplier is added
            } else {
                // mario is touching donkey kong without hammer
                setGameWon(false); // game is lost
            }
            setGameOver(true); // game is over
        }

        // donkey kong has no more health and game is won
        if (getDonkeyKong().getHealth() <= 0) {
            setGameWon(true);
            setGameOver(true);
        }

        // mario has been hit by bullet and game is lost
        if (getMario().isHit()) {
            setGameWon(false);
            setGameOver(true);
        }

        // mario is touching barrel
        for (Barrel barrel : getBarrels()) {
            if (getMario().isTouching(barrel)) {
                if (getMario().hasHammer()) {
                    // mario has hammer and destroys barrel
                    getStats().barrelDestroyed(); // increase points
                    barrel.destroy(); // destroy barrel
                } else {
                    // mario is touching barrel without hammer
                    setGameWon(false); // game is lost
                    setGameOver(true); // game is over
                }
            }
        }

        // mario is touching monkey
        for (Monkey monkey : getNormalMonkeys()) {
            if (getMario().isTouching(monkey)) {
                if (getMario().hasHammer()) {
                    // mario touching monkey with hammer so monkey is destroyed
                    getStats().monkeyDestroyed(); // increase points
                    monkey.destroy();
                } else {
                    // mario is touching monkey without hammer
                    setGameWon(false); // game is lost
                    setGameOver(true); // game is over
                }
            }
        }

        // mario is touching intelligent monkey
        for (IntelligentMonkey monkey : getIntelligentMonkeys()) {
            if (getMario().isTouching(monkey)) {
                // mario touching monkey with hammer so monkey is destroyed
                if (getMario().hasHammer()) {
                    getStats().monkeyDestroyed(); // increase points
                    monkey.destroy();
                } else {
                    // mario is touching monkey without hammer
                    setGameWon(false); // game is lost
                    setGameOver(true); // game is over
                }
            }
        }

        // remaining time has run out so game is lost
        if (getStats().getRemainingTime() <= 0) {
            setGameWon(false);
            setGameOver(true);
        }

        // set points to 0 if game is lost
        if (getGameOver() && !getGameWon()) {
            getStats().setPoints(0);
        }
    }

    /**
     * Update the game objects based on user input allowing for objects
     * to interact, change position and display sprites.
     * @param input The current mouse/keyboard input.
     */
    @Override
    public void update(Input input) {
        drawBackground(); // draw black background

        for (Platform platform : getPlatforms()) {
            platform.display(); // display platforms
        }

        for (Barrel barrel: getBarrels()) {
            if (getMario().jumpingOverBarrel(barrel) && !barrel.isDestroyed()) {
                // mario is jumping over barrel
                getStats().jumpedOver(); // add points
                getStats().changeBarrelScore(false); // stop adding points whilst midair
            }
            barrel.update(getPlatforms()); // update barrel status
        }

        for (Ladder ladder: getLadders()) {
            ladder.update(getPlatforms()); // update ladder status
        }

        for (Blaster blaster: getBlasters()) {
            blaster.display(); // display blasters
        }

        getDonkeyKong().update(getPlatforms()); // update donkey kong status

        for (IntelligentMonkey monkey : getIntelligentMonkeys()) {
            monkey.update(getPlatforms(), getMario(), getStats()); // update intelligent monkey status
        }

        for (Monkey monkey : getNormalMonkeys()) {
            monkey.update(getPlatforms()); // update normal monkey status
        }

        for (Hammer hammer : getHammers()) {
            hammer.display(); // display hammer
        }

        getMario().update(input, getPlatforms(), getLadders(), getHammers(), getBlasters(), getNormalMonkeys(),
                getIntelligentMonkeys(), getDonkeyKong(), getStats()); // update mario status

        // check if mario is on platform
        if (getMario().onPlatform(getPlatforms())) {
            // allow mario to gain points jumping over barrels now he is on a platform and not midair
            getStats().changeBarrelScore(true);
        }

        getStats().display(getDonkeyKong().getHealth(), getMario().getBullets()); // display game stats
        getStats().increaseFrame(); // increase time played
        checkGameStatus(); // check if game is over
    }
}
