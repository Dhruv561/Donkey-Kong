import bagel.*;
import java.util.Properties;

/**
 * This class {@code Screen} and is responsible for initialising and rendering
 * the start screen for the game
 */
public class StartScreen extends Screen {
    private final String title;
    private final String prompt;
    private final String font;

    /**
     * Initialises start screen based off game and message properties by setting the start
     * screens title and prompt text and loading the font
     * @param gameProps
     * @param messageProps
     */
    public StartScreen(Properties gameProps, Properties messageProps) {
        super(gameProps, messageProps);
        this.title = MESSAGE_PROPS.getProperty("home.title");
        this.prompt = MESSAGE_PROPS.getProperty("home.prompt");
        this.font = GAME_PROPS.getProperty("font");
    }

    /**
     * Displays the start screen by rendering the text in title and prompt
     */
    @Override
    public void display() {
        // loads background image
        drawBackground();

        // creates title font
        Font titleFont = new Font(font, Integer.parseInt(GAME_PROPS.getProperty("home.title.fontSize")));
        // draws title text using title font
        titleFont.drawString(title, getCenterX(titleFont, title),
                Integer.parseInt(GAME_PROPS.getProperty("home.title.y")));

        // creates prompt font
        Font promptFont = new Font(font, Integer.parseInt(GAME_PROPS.getProperty("home.prompt.fontSize")));
        // draws prompt text using prompt font
        promptFont.drawString(prompt, getCenterX(promptFont, prompt),
                Integer.parseInt(GAME_PROPS.getProperty("home.prompt.y")));
    }
}
