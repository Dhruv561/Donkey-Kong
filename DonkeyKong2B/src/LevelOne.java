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
public class LevelOne extends GameScreen {
    private final static int LEVEL = 1;

    /**
     * Initialises game stats and loads level
     * @param gameProps game properties
     * @param messageProps message properties
     */
    public LevelOne(Properties gameProps, Properties messageProps) {
        super(gameProps, messageProps);
        loadLevel(); // initialise all game objects
    }

    /**
     * Implementation method in Screen class which initialises all game objects for level 1
     * such as getMario(), donkey kong, hammer, platforms, ladders and barrels
     */
    @Override
    public void loadLevel() {
        // create mario at appropriate coordinates
        setMario(new Mario(Integer.parseInt(getGAME_PROPS().getProperty("mario.level1").split(",")[0]),
                Integer.parseInt(getGAME_PROPS().getProperty("mario.level1").split(",")[1])));
        // create donkey kong at appropriate coordinates
        setDonkeyKong(new DonkeyKong(Integer.parseInt(getGAME_PROPS().getProperty("donkey.level1").split(",")[0]),
                Integer.parseInt(getGAME_PROPS().getProperty("donkey.level1").split(",")[1])));
        createPlatforms(LEVEL);
        createBarrels(LEVEL);
        createLadders(LEVEL);
        createHammers(LEVEL);
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
        // mario touching donkey kong
        if (getMario().isTouching(getDonkeyKong())) {
            if (getMario().hasHammer()) {
                // mario hits donkey kong with hammer
                setGameWon(true);
            } else {
                // mario touching donkey kong without hammer
                setGameWon(false);
            }
            setGameOver(true); // game is over
        }

        // mario touching barrels
        for (Barrel barrel : getBarrels()) {
            if (getMario().isTouching(barrel)) {
                if (getMario().hasHammer()) {
                    // barrel destroyed by hammer
                    getStats().barrelDestroyed(); // increase points
                    barrel.destroy();
                } else {
                    // mario touching barrel without hammer
                    setGameWon(false); // game is lost
                    setGameOver(true); // game is over
                }
            }
        }

        // no more remaining time
        if (getStats().getRemainingTime() <= 0) {
            setGameWon(false); // game lost
            setGameOver(true); // game over
        }

        // set points to 0 if game has been lost
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

        // display platforms
        for (Platform platform : getPlatforms()) {
            platform.display();
        }

        for (Barrel barrel: getBarrels()) {
            // increase points if mario jumping over barrel
            if (getMario().jumpingOverBarrel(barrel) && !barrel.isDestroyed()) {
                getStats().jumpedOver(); // increase points
                getStats().changeBarrelScore(false); // stop adding further points to avoid double counting
            }
            barrel.update(getPlatforms()); // update barrel status
        }

        for (Ladder ladder: getLadders()) {
            ladder.update(getPlatforms()); // update ladder status
        }

        for (Hammer hammer : getHammers()) {
            hammer.display(); // display hammers
        }

        getMario().update(input, getPlatforms(), getLadders(), getHammers()); // update mario status
        getDonkeyKong().update(getPlatforms()); // update donkey kong status

        if (getMario().onPlatform(getPlatforms())) {
            // allow mario to gain points jumping over barrels now he is on a platform and not midair
            getStats().changeBarrelScore(true);
        }
        getStats().display(); // display game stats
        getStats().increaseFrame(); // increase time played
        checkGameStatus(); // check if game is over
    }
}
