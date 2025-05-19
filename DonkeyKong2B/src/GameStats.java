import bagel.*;
import java.util.Properties;

/**
 * Class which is used to track and display different stats for a level including
 * points, score, donkey kong health and bullets remaining. This class is responsible
 * for updating the score when mario destroys objects. It is also used to track
 * the remaining time in the game.
 */
public class GameStats {
    private int points = 0;
    private boolean barrelJump = true;
    private final static int DESTROY_BARREL = 100;
    private final static int BARREL_JUMP_OVER = 30;
    private final static int DESTROY_MONKEY = 100;
    private final static int TIME_MULTIPLIER = 3;

    private int currentFrame = 0;
    private final int MAX_FRAME;
    private final static int SECOND = 60;

    private double scoreX, scoreY;
    private double timeX, timeY;
    private double healthX, healthY;
    private double bulletX, bulletY;

    private final Font FONT;
    private final Properties GAME_PROPS;
    private final Properties MESSAGE_PROPS;
    private final static int OFFSHIFT = 30;

    /**
     * Initialises fonts, x and y coordinates for displaying stats text
     * @param gameProps game properties
     * @param messageProps message properties
     */
    public GameStats(Properties gameProps, Properties messageProps) {
        this.GAME_PROPS = gameProps;
        this.MESSAGE_PROPS = messageProps;
        this.FONT = new Font(gameProps.getProperty("font"),
                             Integer.parseInt(gameProps.getProperty("gamePlay.score.fontSize")));
        this.MAX_FRAME = Integer.parseInt(gameProps.getProperty("gamePlay.maxFrames"));
        this.scoreX = Integer.parseInt(gameProps.getProperty("gamePlay.score.x"));
        this.scoreY = Integer.parseInt(gameProps.getProperty("gamePlay.score.y"));
        this.timeX = scoreX;
        this.timeY = scoreY + OFFSHIFT;
        String[] health = gameProps.getProperty("gamePlay.donkeyhealth.coords").split(",");
        this.healthX = Integer.parseInt(health[0]);
        this.healthY = Integer.parseInt(health[1]);
        this.bulletX = healthX;
        this.bulletY = healthY + OFFSHIFT;
    }

    /**
     * Returns points
     * @return points
     */
    public int getPoints() {
        return this.points;
    }

    /**
     * Sets points
     * @param points points
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Stops changing barrel score over repeated frames when mario is mid jump
     * to avoid over counting score
     * @param changeScore boolean to tell if score should change
     */
    public void changeBarrelScore(boolean changeScore) {
        this.barrelJump = changeScore;
    }

    /**
     * Adds points for destroying barrels
     */
    public void barrelDestroyed() {
        points += DESTROY_BARREL;
    }

    /**
     * Adds points for mario jumping over barrels
     */
    public void jumpedOver() {
        if (barrelJump) {
            points += BARREL_JUMP_OVER;
        }
    }

    /**
     * Adds points for mario destroying monkeys
     */
    public void monkeyDestroyed() {
        points += DESTROY_MONKEY;
    }

    /**
     * Adds points for remaining time when level is complete
     */
    public void timeMultiplier() {
        points += getRemainingTime() * TIME_MULTIPLIER;
    }

    /**
     * Calculates and returns remaining time using max and current frames
     * @return int which represents remaining time in the game
     */
    public int getRemainingTime() {
        return (MAX_FRAME - currentFrame) / SECOND;
    }

    /**
     * Calculates and returns remaining time using max frame and specific frame provided
     * @param frame specific frame to track time from
     * @return int which represents remaining time in the game
     */
    public int getRemainingTime(int frame) {
        return (MAX_FRAME - frame) / SECOND;
    }

    /**
     * Increases current frame
     */
    public void increaseFrame() {
        currentFrame++;
    }

    /**
     * Used to display score and time remaining for level 1
     */
    public void display() {
        FONT.drawString("SCORE " + points, scoreX, scoreY);
        FONT.drawString("TIME LEFT " + getRemainingTime(), timeX, timeY);
    }

    /**
     * Used to display score, time remaining, bullets and donkey kong health for level 2
     * @param health int donkey kong health
     * @param bullets int bullets remaining
     */
    public void display(int health, int bullets) {
        FONT.drawString("SCORE " + points, scoreX, scoreY);
        FONT.drawString("TIME LEFT " + getRemainingTime(), timeX, timeY);
        FONT.drawString("DONKEY HEALTH " + health, healthX, healthY);
        FONT.drawString("BULLETS " + bullets, bulletX, bulletY);
    }
}
