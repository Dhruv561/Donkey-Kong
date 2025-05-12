import java.util.Properties;
import bagel.*;

public class LevelTwo extends GameScreen {
    private final int LEVEL = 2;

    public LevelTwo(Properties gameProps, Properties messageProps) {
        super(gameProps, messageProps);
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

    private void isGameOver() {
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

        for (Blaster blaster: blasters) {
            blaster.display();
        }

        for (NormalMonkey monkey : normalMonkeys) {
            monkey.update(platforms);
        }

        for (IntelligentMonkey monkey : intelligentMonkeys) {
            monkey.update(platforms);
        }

        mario.update(input, platforms, ladders, hammer, blasters, normalMonkeys, intelligentMonkeys);
        donkeyKong.update(platforms);
        hammer.display();

        isGameOver();
    }
}
