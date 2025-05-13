import java.util.Properties;
import bagel.*;

public class LevelOne extends GameScreen {
    private final int LEVEL = 1;
    private final GameStats STATS;

    public LevelOne(Properties gameProps, Properties messageProps) {
        super(gameProps, messageProps);
        this.STATS = new GameStats(gameProps, messageProps);
        loadLevel();
    }

    @Override
    public void loadLevel() {
        this.mario = new Mario(Integer.parseInt(GAME_PROPS.getProperty("mario.level1").split(",")[0]),
                Integer.parseInt(GAME_PROPS.getProperty("mario.level1").split(",")[1]));

        this.hammer = new Hammer(Integer.parseInt(GAME_PROPS.getProperty("hammer.level1.1").split(",")[0]),
                Integer.parseInt(GAME_PROPS.getProperty("hammer.level1.1").split(",")[1]));

        this.donkeyKong = new DonkeyKong(Integer.parseInt(GAME_PROPS.getProperty("donkey.level1").split(",")[0]),
                Integer.parseInt(GAME_PROPS.getProperty("donkey.level1").split(",")[1]));
        createPlatforms(LEVEL);
        createBarrels(LEVEL);
        createLadders(LEVEL);
    }

    @Override
    public void display() {}

    public int getPoints() {
        return STATS.getPoints();
    }

    private void isGameOver() {
        if (mario.isTouching(donkeyKong)) {
            if (mario.hasHammer()) {
                gameWon = true;
                STATS.timeMultiplier();
            } else {
                gameWon = false;
            }
            gameOver = true;
        }

        for (Barrel barrel : barrels) {
            if (mario.isTouching(barrel)) {
                if (mario.hasHammer()) {
                    STATS.barrelDestroyed();
                    barrel.destroy();
                } else {
                    gameWon = false;
                    gameOver = true;
                }
            }
        }

        if (STATS.getRemainingTime() <= 0) {
            gameWon = false;
            gameOver = true;
        }

        if (gameOver && !gameWon) {
            STATS.setPoints(0);
        }
    }

    @Override
    public void update(Input input) {
        drawBackground();
        for (Platform platform : platforms) {
            platform.display();
        }

        for (Barrel barrel: barrels) {
            if (mario.jumpingOverBarrel(barrel) && !barrel.isDestroyed()) {
                STATS.jumpedOver();
                STATS.changeBarrelScore(false);
            }
            barrel.update(platforms, mario);
        }

        for (Ladder ladder: ladders) {
            ladder.display();
        }

        mario.update(input, platforms, ladders, hammer);
        donkeyKong.update(platforms);
        hammer.display();

        if (mario.onPlatform(platforms)) {
            STATS.changeBarrelScore(true);
        }
        STATS.display();
        STATS.increaseFrame();
        isGameOver();
    }
}
