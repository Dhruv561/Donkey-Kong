import bagel.*;
import java.util.Properties;

/**
 * The main class for the Shadow Donkey Kong game.
 * This class extends {@code AbstractGame} and is responsible for managing game
 * states and switching between start, level and end screens.
 */
public class ShadowDonkeyKong extends AbstractGame {
    private final Properties GAME_PROPS;
    private final Properties MESSAGE_PROPS;
    private final StartScreen START_SCREEN;
    private EndScreen endScreen;
    private LevelOne levelOne;
    private LevelTwo levelTwo;
    private static double screenWidth;
    private static double screenHeight;

    private boolean gameStarted = false;
    private boolean gameOver = false;
    private boolean levelOneComplete = false;
    private boolean levelTwoComplete = false;

    public ShadowDonkeyKong(Properties gameProps, Properties messageProps) {
        super(Integer.parseInt(gameProps.getProperty("window.width")),
                Integer.parseInt(gameProps.getProperty("window.height")),
                messageProps.getProperty("home.title"));
        this.GAME_PROPS = gameProps;
        this.MESSAGE_PROPS = messageProps;
        this.START_SCREEN = new StartScreen(GAME_PROPS, MESSAGE_PROPS);
        this.levelOne = new LevelOne(GAME_PROPS, MESSAGE_PROPS);
        screenWidth = Integer.parseInt(gameProps.getProperty("window.width"));
        screenHeight = Integer.parseInt(gameProps.getProperty("window.height"));
    }

    /**
     * Render the relevant screen based on the keyboard input given by the user and the status of the gameplay.
     * @param input The current mouse/keyboard input.
     */
    @Override
    protected void update(Input input) {
        // quit game if escape is pressed
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }
        // show start screen if game hasn't started
        if (!gameStarted && !gameOver) {
            START_SCREEN.display();
            // load level 1 if enter or 1 is pressed
            if (!gameStarted && (input.wasPressed(Keys.ENTER) || input.wasPressed(Keys.NUM_1))) {
                gameStarted = true;
                // load level 2 if 2 is pressed
            } else if (!gameStarted && input.wasPressed(Keys.NUM_2)) {
                gameStarted = true;
                levelOneComplete = true; // set level one to complete
                int points = levelOne.getPoints();
                levelTwo = new LevelTwo(GAME_PROPS, MESSAGE_PROPS, points); // create level 2 based off level 1 points
            }
        }

        // show game screen where main gameplay occurs for level 1
        if (gameStarted && !gameOver && !levelOneComplete) {
            levelOne.update(input);

            // level 1 is over
            if (levelOne.getGameOver()) {
                if (levelOne.getGameWon()) {
                    // level 1 is successfully completed
                    levelOneComplete = true;
                    int points = levelOne.getPoints();
                    levelTwo = new LevelTwo(GAME_PROPS, MESSAGE_PROPS, points); // initialise level 2
                } else {
                    // level 1 is lost
                    gameOver = true;
                }
            }
        }

        // show game screen for level 2
        if (gameStarted && !gameOver && levelOneComplete && !levelTwoComplete) {
            levelTwo.update(input);

            // level 2 is over
            if (levelTwo.getGameOver()) {
                if (levelTwo.getGameWon()) {
                    // level two is won
                    levelTwoComplete = true;
                }
                gameOver = true;
            }
        }

        // game has ended
        if (gameOver) {
            int points;
            if (levelOneComplete && levelTwoComplete) {
                // both levels are successfully completed
                points = levelTwo.getPoints();
            } else  {
                // either level has been failed
                points = 0;
            }

            // create and display endscreen
            endScreen = new EndScreen(levelTwoComplete, points, GAME_PROPS, MESSAGE_PROPS);
            endScreen.display();
            if (input.wasPressed(Keys.SPACE)) {
                // restart game by resetting variables and go to start screen
                gameOver = false;
                gameStarted = false;
                levelOneComplete = false;
                levelTwoComplete = false;
                levelOne = new LevelOne(GAME_PROPS, MESSAGE_PROPS); // create new level 1
            }
        }
    }

    /**
     * Retrieves the width of the game screen.
     * @return The width of the screen in pixels.
     */
    public static double getScreenWidth() {
        return screenWidth;
    }

    /**
     * Retrieves the height of the game screen.
     * @return The height of the screen in pixels.
     */
    public static double getScreenHeight() {
        return screenHeight;
    }

    /**
     * The main entry point of the Shadow Donkey Kong game.
     * This method loads the game properties and message files, initializes the game,
     * and starts the game loop.
     * @param args Command-line arguments (not used in this game).
     */
    public static void main(String[] args) {
        Properties gameProps = IOUtils.readPropertiesFile("res/app.properties");
        Properties messageProps = IOUtils.readPropertiesFile("res/message.properties");
        ShadowDonkeyKong game = new ShadowDonkeyKong(gameProps, messageProps);
        game.run();
    }
}


