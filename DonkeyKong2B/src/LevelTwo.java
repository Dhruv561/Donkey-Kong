import java.util.Properties;
import bagel.*;

public class LevelTwo extends GameScreen {
    private final int LEVEL = 2;
    private final GameStats STATS;

    public LevelTwo(Properties gameProps, Properties messageProps) {
        super(gameProps, messageProps);
        this.STATS = new GameStats(gameProps, messageProps);
        loadLevel();
    }

    @Override
    public void loadLevel() {
        this.mario = new Mario(Integer.parseInt(GAME_PROPS.getProperty("mario.level2").split(",")[0]),
                               Integer.parseInt(GAME_PROPS.getProperty("mario.level2").split(",")[1]));

        this.hammer = new Hammer(Integer.parseInt(GAME_PROPS.getProperty("hammer.level2.1").split(",")[0]),
                                 Integer.parseInt(GAME_PROPS.getProperty("hammer.level2.1").split(",")[1]));

        this.donkeyKong = new DonkeyKong(Integer.parseInt(GAME_PROPS.getProperty("donkey.level2").split(",")[0]),
                                         Integer.parseInt(GAME_PROPS.getProperty("donkey.level2").split(",")[1]));
        createPlatforms(LEVEL);
        createBarrels(LEVEL);
        createLadders(LEVEL);
        createBlasters();
        createMonkeys();
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

        if (donkeyKong.getHealth() <= 0) {
            gameWon = true;
            gameOver = true;
        }

        if (mario.isHit()) {
            gameWon = false;
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

        for (NormalMonkey monkey : normalMonkeys) {
            if (mario.isTouching(monkey)) {
                if (mario.hasHammer()) {
                    STATS.monkeyDestroyed();
                    monkey.destroy();
                } else {
                    gameWon = false;
                    gameOver = true;
                }
            }
        }

        for (IntelligentMonkey monkey : intelligentMonkeys) {
            if (mario.isTouching(monkey)) {
                if (mario.hasHammer()) {
                    STATS.monkeyDestroyed();
                    monkey.destroy();
                } else {
                    gameWon = false;
                    gameOver = true;
                }
            }
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

        for (Blaster blaster: blasters) {
            blaster.display();
        }

        for (NormalMonkey monkey : normalMonkeys) {
            monkey.update(platforms);
        }

        for (IntelligentMonkey monkey : intelligentMonkeys) {
            monkey.update(platforms, mario);
        }

        mario.update(input, platforms, ladders, hammer, barrels, blasters, normalMonkeys, intelligentMonkeys, donkeyKong, STATS);
        donkeyKong.update(platforms);
        hammer.display();

        if (mario.onPlatform(platforms)) {
            STATS.changeBarrelScore(true);
        }
        STATS.display(donkeyKong.getHealth(), mario.getBullets());
        STATS.increaseFrame();
        isGameOver();
    }
}
