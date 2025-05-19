import bagel.Input;
import java.util.Properties;

/**
 * This class extends {@code Screen} and is responsible for initialising game objects for levels 1 and 2
 */
public abstract class GameScreen extends Screen {
    private Mario mario;
    private DonkeyKong donkeyKong;
    private Platform[] platforms;
    private Barrel[] barrels;
    private Ladder[] ladders;
    private Hammer[] hammers;
    private Monkey[] normalMonkeys;
    private IntelligentMonkey[] intelligentMonkeys;
    private Blaster[] blasters;

    private boolean gameOver = false;
    private boolean gameWon = false;
    private final GameStats STATS;

    /**
     * Initialises screen based on game and message properties
     *
     * @param gameProps    game properties
     * @param messageProps message properties
     */
    public GameScreen(Properties gameProps, Properties messageProps) {
        super(gameProps, messageProps);
        this.STATS = new GameStats(gameProps, messageProps);
    }

    /**
     * Returns points earnt
     * @return points
     */
    public int getPoints() {
        return STATS.getPoints();
    }

    /**
     * Sets donkey kong
     * @param donkeyKong donkey kong
     */
    public void setDonkeyKong(DonkeyKong donkeyKong) {
        this.donkeyKong = donkeyKong;
    }

    /**
     * Sets mario
     * @param mario mario
     */
    public void setMario(Mario mario) {
        this.mario = mario;
    }

    /**
     * Gets mario object
     * @return mario object
     */
    public Mario getMario() {
        return mario;
    }

    /**
     * gets donkey kong object
     * @return donkey kong object
     */
    public DonkeyKong getDonkeyKong() {
        return donkeyKong;
    }

    /**
     * Returns all platforms
     * @return array of all platforms
     */
    public Platform[] getPlatforms() {
        return platforms;
    }

    /**
     * Returns all barrels
     * @return array of barrels
     */
    public Barrel[] getBarrels() {
        return barrels;
    }

    /**
     * Returns all ladders
     * @return array of ladders
     */
    public Ladder[] getLadders() {
        return ladders;
    }

    /**
     * Returns all hammers
     * @return array of hammers
     */
    public Hammer[] getHammers() {
        return hammers;
    }

    /**
     * Returns all normal monkeys
     * @return array of normal monkeys
     */
    public Monkey[] getNormalMonkeys() {
        return normalMonkeys;
    }

    /**
     * Returns all intelligent monkeys
     * @return array of all intelligent monkeys
     */
    public IntelligentMonkey[] getIntelligentMonkeys() {
        return intelligentMonkeys;
    }

    /**
     * Returns all blasters
     * @return array of blasters
     */
    public Blaster[] getBlasters() {
        return blasters;
    }

    /**
     * Returns game stats object
     * @return game stats object
     */
    public GameStats getStats() {
        return STATS;
    }

    /**
     * Sets game won status
     * @param gameWon boolean if game is won
     */
    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }

    /**
     * Sets game over status
     * @param gameOver boolean if game is over
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    /**
     * Return if game as been won
     * @return boolean if game has been won
     */
    public boolean getGameWon() {
        return this.gameWon;
    }

    /**
     * Returns if game is over
     * @return boolean if game is over
     */
    public boolean getGameOver() {
        return this.gameOver;
    }

    /**
     * Abstract method to update the state of the game screen
     * @param input The current mouse/keyboard input.
     */
    public abstract void update(Input input);

    /**
     * Abstract method to initialise game objects for each level
     */
    public abstract void loadLevel();

    /**
     * Abstract method to check if game is over and won
     */
    public abstract void checkGameStatus();

    /**
     * Empty implementation of abstract display method from Screen class
     */
    public void display() {}

    /**
     * Initialises all platforms for level
     * @param level current game level
     */
    public void createPlatforms(int level) {
        String[] platformCoordinates = getGAME_PROPS().getProperty("platforms.level" + level).split(";");
        platforms = new Platform[platformCoordinates.length];

        for (int i = 0; i < platformCoordinates.length; i++) {
            double x = Integer.parseInt(platformCoordinates[i].split(",")[0]);
            double y = Integer.parseInt(platformCoordinates[i].split(",")[1]);
            platforms[i] = new Platform(x, y);
        }
    }

    /**
     * Initialises all barrels for level
     * @param level current game level
     */
    public void createBarrels(int level) {
        int barrelCount = Integer.parseInt(getGAME_PROPS().getProperty("barrel.level" + level + ".count"));
        barrels = new Barrel[barrelCount];
        for (int i = 1; i <= barrelCount; i++) {
            double x = Integer.parseInt(getGAME_PROPS().getProperty("barrel.level" + level + "." + i).split(",")[0]);
            double y = Integer.parseInt(getGAME_PROPS().getProperty("barrel.level" + level + "." + i).split(",")[1]);
            barrels[i - 1] = new Barrel(x, y);
        }
    }

    /**
     * Initialises all hammers for level
     * @param level current game level
     */
    public void createHammers(int level) {
        int hammerCount = Integer.parseInt(getGAME_PROPS().getProperty("hammer.level" + level + ".count"));
        hammers = new Hammer[hammerCount];
        for (int i = 1; i <= hammerCount; i++) {
            double x = Integer.parseInt(getGAME_PROPS().getProperty("hammer.level" + level + "." + i).split(",")[0]);
            double y = Integer.parseInt(getGAME_PROPS().getProperty("hammer.level" + level + "." + i).split(",")[1]);
            hammers[i - 1] = new Hammer(x, y);
        }
    }

    /**
     * Initialises all ladders for level
     * @param level current game level
     */
    public void createLadders(int level) {
        int ladderCount = Integer.parseInt(getGAME_PROPS().getProperty("ladder.level" + level + ".count"));
        ladders = new Ladder[ladderCount];
        for (int i = 1; i <= ladderCount; i++) {
            double x = Integer.parseInt(getGAME_PROPS().getProperty("ladder.level" + level + "." + i).split(",")[0]);
            double y = Integer.parseInt(getGAME_PROPS().getProperty("ladder.level" + level + "." + i).split(",")[1]);
            ladders[i - 1] = new Ladder(x, y);
        }
    }

    /**
     * Initialises blasters for level
     */
    public void createBlasters() {
        int blasterCount = Integer.parseInt(getGAME_PROPS().getProperty("blaster.level2.count"));
        blasters = new Blaster[blasterCount];
        for (int i = 1; i <= blasterCount; i++) {
            double x = Integer.parseInt(getGAME_PROPS().getProperty("blaster.level2." + i).split(",")[0]);
            double y = Integer.parseInt(getGAME_PROPS().getProperty("blaster.level2." + i).split(",")[1]);
            blasters[i - 1] = new Blaster(x, y);
        }
    }

    /**
     * Initialises normal monkeys for level
     */
    private void createNormalMonkeys() {
        int normalMonkeyCount = Integer.parseInt(getGAME_PROPS().getProperty("normalMonkey.level2.count"));
        normalMonkeys = new Monkey[normalMonkeyCount];

        for (int i = 1; i <= normalMonkeyCount; i++) {
            String propertyData = getGAME_PROPS().getProperty("normalMonkey.level2." + i);
            String[] splitData = propertyData.split(";");

            String[] coordinates = splitData[0].split(",");
            double x = Double.parseDouble(coordinates[0]);
            double y = Double.parseDouble(coordinates[1]);

            boolean isRight = splitData[1].equals("right");

            String[] movePatternStrings = splitData[2].split(",");
            int[] movePatternInt = new int[movePatternStrings.length];
            for (int j = 0; j < movePatternStrings.length; j++) {
                movePatternInt[j] = Integer.parseInt(movePatternStrings[j]);
            }

            normalMonkeys[i-1] = new Monkey(x, y, isRight, movePatternInt);
        }
    }

    /**
     * Initialises intelligent monkeys for level
     */
    private void createIntelligentMonkeys() {
        int intelligentMonkeyCount = Integer.parseInt(getGAME_PROPS().getProperty("intelligentMonkey.level2.count"));
        intelligentMonkeys = new IntelligentMonkey[intelligentMonkeyCount];

        for (int i = 1; i <= intelligentMonkeyCount; i++) {
            String propertyData = getGAME_PROPS().getProperty("intelligentMonkey.level2." + i);
            String[] splitData = propertyData.split(";");

            String[] coordinates = splitData[0].split(",");
            double x = Double.parseDouble(coordinates[0]);
            double y = Double.parseDouble(coordinates[1]);

            boolean isRight = splitData[1].equals("right");

            String[] movePatternStrings = splitData[2].split(",");
            int[] movePatternInt = new int[movePatternStrings.length];
            for (int j = 0; j < movePatternStrings.length; j++) {
                movePatternInt[j] = Integer.parseInt(movePatternStrings[j]);
            }

            intelligentMonkeys[i-1] = new IntelligentMonkey(x, y, isRight, movePatternInt);
        }
    }

    /**
     * Initialises all monkeys for level
     */
    public void createMonkeys() {
        createNormalMonkeys();
        createIntelligentMonkeys();
    }
}
