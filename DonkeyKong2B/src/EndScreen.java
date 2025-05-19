import bagel.*;
import java.util.Properties;

/**
 * This class extends {@code Screen} and is responsible for initialising and rendering
 * the end screen for the game
 */
public class EndScreen extends Screen {
    private final int PROMPT_OFFSHIFT = 100;
    private final int SCORE_OFFSHIFT = 60;

    private final String title;
    private final String score;
    private final String prompt;
    private final String font;

    private boolean gameWon;
    private int points;

    /**
     * Initialises end screen based off game and message properties by setting the end screens title and prompt
     *  text based off whether the game has been won or lost and how many points have been scored.
     * @param gameWon contains status of game
     * @param points number of points scored
     * @param gameProps game properties
     * @param messageProps message properties
     */
    public EndScreen(boolean gameWon, int points, Properties gameProps, Properties messageProps) {
        super(gameProps, messageProps);
        this.gameWon = gameWon;
        this.points = points;

        if (this.gameWon) {
            // print winning message
            title = getMESSAGE_PROPS().getProperty("gameEnd.won");
        } else {
            // print losing message
            title = getMESSAGE_PROPS().getProperty("gameEnd.lost");
        }
        score = getMESSAGE_PROPS().getProperty("gameEnd.score") +  " " + this.points;
        prompt = getMESSAGE_PROPS().getProperty("gameEnd.continue");
        font = getGAME_PROPS().getProperty("font");
    }

    /**
     * Displays the end screen by rendering the text in title, score and prompt
     */
    @Override
    public void display() {
        // draws background image
        drawBackground();

        // creates title font
        Font titleFont = new Font(font, Integer.parseInt(getGAME_PROPS().getProperty("gameEnd.status.fontSize")));
        // draws title text using title font
        titleFont.drawString(title, getCenterX(titleFont, title),
                Integer.parseInt(getGAME_PROPS().getProperty("gameEnd.status.y")));

        // creates prompt font
        Font promptFont = new Font(font, Integer.parseInt(getGAME_PROPS().getProperty("gameEnd.scores.fontSize")));
        // draws score text using prompt font
        promptFont.drawString(score, getCenterX(promptFont, score),
                Integer.parseInt(getGAME_PROPS().getProperty("gameEnd.status.y")) + SCORE_OFFSHIFT);
        // draws prompt text using prompt font
        promptFont.drawString(prompt, getCenterX(promptFont, prompt),
                Integer.parseInt(getGAME_PROPS().getProperty("window.height")) - PROMPT_OFFSHIFT);
    }
}
