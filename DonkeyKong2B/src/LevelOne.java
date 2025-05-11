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
        this.mario = new Mario();
        this.hammer = new Hammer();
        this.donkeyKong = new DonkeyKong();
        createPlatforms(LEVEL);
        createBarrels(LEVEL);
        createLadders(LEVEL);
    }

    @Override
    public void display() {

    }

    @Override
    public void update() {

    }
}
