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
    public void display() {

    }

    @Override
    public void update() {

    }
}
