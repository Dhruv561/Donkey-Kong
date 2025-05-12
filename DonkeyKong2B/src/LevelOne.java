import java.util.Properties;
import bagel.*;

public class LevelOne extends GameScreen {
    private final int LEVEL = 1;

    public LevelOne(Properties gameProps, Properties messageProps) {
        super(gameProps, messageProps);
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

    public void isGameOver() {
        if (mario.isTouching(donkeyKong)) {
            gameWon = mario.hasHammer();
            gameOver = true;
        }

        for (Barrel barrel : barrels) {
            if (mario.isTouching(barrel)) {
                if (mario.hasHammer()) {
                    //increase points
                    barrel.destroy();
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
            barrel.update(platforms);
        }

        for (Ladder ladder: ladders) {
            ladder.display();
        }

        mario.update(input, platforms, ladders, hammer);
        donkeyKong.update(platforms);
        hammer.display();
        isGameOver();
    }
}
