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
        setMario(new Mario(Integer.parseInt(getGAME_PROPS().getProperty("mario.level2").split(",")[0]),
                Integer.parseInt(getGAME_PROPS().getProperty("mario.level2").split(",")[1])));
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
        if (getMario().isTouching(getDonkeyKong())) {
            if (getMario().hasHammer()) {
                setGameWon(true);
                getStats().timeMultiplier();
            } else {
                setGameWon(false);
            }
            setGameOver(true);
        }

        if (getDonkeyKong().getHealth() <= 0) {
            setGameWon(true);
            setGameOver(true);
        }

        if (getMario().isHit()) {
            setGameWon(false);
            setGameOver(true);
        }

        for (Barrel barrel : getBarrels()) {
            if (getMario().isTouching(barrel)) {
                if (getMario().hasHammer()) {
                    getStats().barrelDestroyed();
                    barrel.destroy();
                } else {
                    setGameWon(false);
                    setGameOver(true);
                }
            }
        }

        for (Monkey monkey : getNormalMonkeys()) {
            if (getMario().isTouching(monkey)) {
                if (getMario().hasHammer()) {
                    getStats().monkeyDestroyed();
                    monkey.destroy();
                } else {
                    setGameWon(false);
                    setGameOver(true);
                }
            }
        }

        for (IntelligentMonkey monkey : getIntelligentMonkeys()) {
            if (getMario().isTouching(monkey)) {
                if (getMario().hasHammer()) {
                    getStats().monkeyDestroyed();
                    monkey.destroy();
                } else {
                    setGameWon(false);
                    setGameOver(true);
                }
            }
        }

        if (getStats().getRemainingTime() <= 0) {
            setGameWon(false);
            setGameOver(true);
        }

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
        drawBackground();
        for (Platform platform : getPlatforms()) {
            platform.display();
        }

        for (Barrel barrel: getBarrels()) {
            if (getMario().jumpingOverBarrel(barrel) && !barrel.isDestroyed()) {
                getStats().jumpedOver();
                getStats().changeBarrelScore(false);
            }
            barrel.update(getPlatforms());
        }

        for (Ladder ladder: getLadders()) {
            ladder.display();
        }

        for (Blaster blaster: getBlasters()) {
            blaster.display();
        }

        getDonkeyKong().update(getPlatforms());

        for (IntelligentMonkey monkey : getIntelligentMonkeys()) {
            monkey.update(getPlatforms(), getMario(), getStats());
        }

        for (Monkey monkey : getNormalMonkeys()) {
            monkey.update(getPlatforms());
        }

        for (Hammer hammer : getHammers()) {
            hammer.display();
        }

        getMario().update(input, getPlatforms(), getLadders(), getHammers(), getBlasters(), getNormalMonkeys(),
                getIntelligentMonkeys(), getDonkeyKong(), getStats());

        if (getMario().onPlatform(getPlatforms())) {
            getStats().changeBarrelScore(true);
        }
        getStats().display(getDonkeyKong().getHealth(), getMario().getBullets());
        getStats().increaseFrame();
        checkGameStatus();
    }
}
