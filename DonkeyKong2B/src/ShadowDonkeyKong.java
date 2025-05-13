import bagel.*;
import java.util.Properties;

/**
 * The main class for the Shadow Donkey Kong game.
 * This class extends {@code AbstractGame} and is responsible for managing game
 * states and switching between start, game and end screens.
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
        this.levelTwo = new LevelTwo(GAME_PROPS, MESSAGE_PROPS);
        screenWidth = Integer.parseInt(gameProps.getProperty("window.width"));
        screenHeight = Integer.parseInt(gameProps.getProperty("window.height"));
    }

    public Properties getGameProps() {
        return this.GAME_PROPS;
    }

    public Properties getMessageProps() {
        return this.MESSAGE_PROPS;
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
            // start the game if enter is pressed
            if (!gameStarted && (input.wasPressed(Keys.ENTER) || input.wasPressed(Keys.NUM_1))) {
                gameStarted = true;
            } else if (!gameStarted && input.wasPressed(Keys.NUM_2)) {
                gameStarted = true;
                levelOneComplete = true;
            }
        }

        // show game screen where main gameplay occurs
        if (gameStarted && !gameOver && !levelOneComplete) {
            levelOne.update(input);

            // game is over
            if (levelOne.gameOver()) {
                if (levelOne.gameWon()) {
                    levelOneComplete = true;
                } else {
                    gameOver = true;
                }
            }
        }

        if (gameStarted && !gameOver && levelOneComplete && !levelTwoComplete) {
            levelTwo.update(input);

            // game is over
            if (levelTwo.gameOver()) {
                if (levelTwo.gameWon()) {
                    levelTwoComplete = true;
                } else {
                    gameOver = true;
                }
            }
        }

        if (gameOver) {
            // display end screen
            int points = levelOne.gameWon() && levelTwo.gameWon() ? levelOne.getPoints() + levelTwo.getPoints() : 0;
            endScreen = new EndScreen(levelOne.gameWon(), points, GAME_PROPS, MESSAGE_PROPS);
            endScreen.display();
            if (input.wasPressed(Keys.SPACE)) {
                // restart game and go to start screen
                gameOver = false;
                levelOne = new LevelOne(GAME_PROPS, MESSAGE_PROPS);
            }
        }
    }

    /**
     * Retrieves the width of the game screen.
     *
     * @return The width of the screen in pixels.
     */
    public static double getScreenWidth() {
        return screenWidth;
    }

    /**
     * Retrieves the height of the game screen.
     *
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
