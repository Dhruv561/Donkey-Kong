import bagel.Input;

import java.util.Properties;

public abstract class GameScreen extends Screen {
    protected Mario mario;
    protected Hammer hammer;
    protected DonkeyKong donkeyKong;
    protected Platform[] platforms;
    protected Barrel[] barrels;
    protected Ladder[] ladders;
    protected NormalMonkey[] normalMonkeys;
    protected IntelligentMonkey[] intelligentMonkeys;
    protected Blaster[] blasters;

    protected boolean gameOver = false;
    protected boolean gameWon = false;

    /**
     * Initialises screen based on game and message properties
     *
     * @param gameProps    game properties
     * @param messageProps message properties
     */
    public GameScreen(Properties gameProps, Properties messageProps) {
        super(gameProps, messageProps);
    }

    public boolean gameOver() {
        return this.gameOver;
    }

    public boolean gameWon() {
        return this.gameWon;
    }

    public abstract void update(Input input);

    public abstract void loadLevel();

    public void createPlatforms(int level) {
        String[] platformCoordinates = GAME_PROPS.getProperty("platforms.level" + level).split(";");
        platforms = new Platform[platformCoordinates.length];

        for (int i = 0; i < platformCoordinates.length; i++) {
            double x = Integer.parseInt(platformCoordinates[i].split(",")[0]);
            double y = Integer.parseInt(platformCoordinates[i].split(",")[1]);
            platforms[i] = new Platform(x, y);
        }
    }

    public void createBarrels(int level) {
        int barrelCount = Integer.parseInt(GAME_PROPS.getProperty("barrel.level" + level + ".count"));
        barrels = new Barrel[barrelCount];
        for (int i = 1; i <= barrelCount; i++) {
            double x = Integer.parseInt(GAME_PROPS.getProperty("barrel.level" + level + "." + i).split(",")[0]);
            double y = Integer.parseInt(GAME_PROPS.getProperty("barrel.level" + level + "." + i).split(",")[1]);
            barrels[i - 1] = new Barrel(x, y);
        }
    }

    public void createLadders(int level) {
        int ladderCount = Integer.parseInt(GAME_PROPS.getProperty("ladder.level" + level + ".count"));
        ladders = new Ladder[ladderCount];
        for (int i = 1; i <= ladderCount; i++) {
            double x = Integer.parseInt(GAME_PROPS.getProperty("ladder.level" + level + "." + i).split(",")[0]);
            double y = Integer.parseInt(GAME_PROPS.getProperty("ladder.level" + level + "." + i).split(",")[1]);
            ladders[i - 1] = new Ladder(x, y);
        }
    }

    public void createBlasters() {
        int blasterCount = Integer.parseInt(GAME_PROPS.getProperty("blaster.level2.count"));
        blasters = new Blaster[blasterCount];
        for (int i = 1; i <= blasterCount; i++) {
            double x = Integer.parseInt(GAME_PROPS.getProperty("blaster.level2." + i).split(",")[0]);
            double y = Integer.parseInt(GAME_PROPS.getProperty("blaster.level2." + i).split(",")[1]);
            blasters[i - 1] = new Blaster(x, y);
        }
    }

    public void createMonkeys() {
        int normalMonkeyCount = Integer.parseInt(GAME_PROPS.getProperty("normalMonkey.level2.count"));
        normalMonkeys = new NormalMonkey[normalMonkeyCount];
        for (int i = 1; i <= normalMonkeyCount; i++) {
            double x = Integer.parseInt(GAME_PROPS.getProperty("normalMonkey.level2." + i).split(",")[0]);
            double y = Integer.parseInt(GAME_PROPS.getProperty("normalMonkey.level2." + i).split(",")[1]);
            normalMonkeys[i - 1] = new NormalMonkey(x, y);
        }

        int intelligentMonkeyCount = Integer.parseInt(GAME_PROPS.getProperty("intelligentMonkey.level2."));
        intelligentMonkeys = new IntelligentMonkey[intelligentMonkeyCount];
        for (int i = 1; i <= intelligentMonkeyCount; i++) {
            double x = Integer.parseInt(GAME_PROPS.getProperty("intelligentMonkey.level2." + i).split(",")[0]);
            double y = Integer.parseInt(GAME_PROPS.getProperty("intelligentMonkey.level2." + i).split(",")[1]);
            intelligentMonkeys[i - 1] = new IntelligentMonkey(x, y);
        }
    }
}
