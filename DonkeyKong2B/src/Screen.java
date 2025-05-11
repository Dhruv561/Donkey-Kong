import bagel.*;
import java.util.Properties;

/**
 * Abstract screen class made to initialise, draw game background
 * and child screens such as start screen, end screen and game screen
 */
public abstract class Screen {
    protected final Properties GAME_PROPS;
    protected final Properties MESSAGE_PROPS;

    /**
     * Initialises screen based on game and message properties
     * @param gameProps game properties
     * @param messageProps message properties
     */
    public Screen (Properties gameProps, Properties messageProps) {
        this.GAME_PROPS = gameProps;
        this.MESSAGE_PROPS = messageProps;
    }

    /**
     * Draws background image constant across all screens based on window dimensions
     */
    public void drawBackground() {
        Image background = new Image(GAME_PROPS.getProperty("backgroundImage"));
        double x = Integer.parseInt(GAME_PROPS.getProperty("window.width"));
        double y = Integer.parseInt(GAME_PROPS.getProperty("window.height"));
        background.draw(x / 2, y / 2);
    }

    /**
     * Abstract display method which must be implemented by child classes to display all items on screen
     */
    public abstract void display();

    /**
     * Calculates and returns centre x coordinate for text based of the window dimensions
     * @param font stores font for text
     * @param string text which the font will display
     * @return double which is x coordinate where the text is centered on screen
     */
    public double getCenterX(Font font, String string) {
        return (Integer.parseInt(GAME_PROPS.getProperty("window.width")) - font.getWidth(string)) / 2.0;
    }
}
