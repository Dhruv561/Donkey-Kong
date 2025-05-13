import bagel.*;
import bagel.util.*;
import java.util.Properties;

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

    public int getPoints() {
        return this.points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void changeBarrelScore(boolean changeScore) {
        this.barrelJump = changeScore;
    }

    public void barrelDestroyed() {
        points += DESTROY_BARREL;
    }

    public void jumpedOver() {
        if (barrelJump) {
            points += BARREL_JUMP_OVER;
        }
    }

    public void monkeyDestroyed() {
        points += DESTROY_MONKEY;
    }

    public void timeMultiplier() {
        points += getRemainingTime() * TIME_MULTIPLIER;
    }

    public int getRemainingTime() {
        return (MAX_FRAME - currentFrame) / SECOND;
    }

    public int getRemainingTime(int frame) {
        return (MAX_FRAME - frame) / SECOND;
    }

    public void increaseFrame() {
        currentFrame++;
    }

    public void display() {
        FONT.drawString("SCORE " + points, scoreX, scoreY);
        FONT.drawString("TIME LEFT " + getRemainingTime(), timeX, timeY);
    }

    public void display(int health, int bullets) {
        FONT.drawString("SCORE " + points, scoreX, scoreY);
        FONT.drawString("TIME LEFT " + getRemainingTime(), timeX, timeY);
        FONT.drawString("DONKEY HEALTH " + health, healthX, healthY);
        FONT.drawString("BULLETS " + bullets, bulletX, bulletY);
    }
}
