import bagel.*;
import bagel.util.*;

public class Hammer extends GameObject implements Collectable {
    private boolean isCollected = false;
    private final static double GRAVITY = 0;
    public static final double HAMMER_TERMINAL_VELOCITY = 0;
    private final static int OFFSCREEN = -100;

    public Hammer(double centreX, double centreY) {
        super(centreX, centreY, new Image("res/hammer.png") , GRAVITY, HAMMER_TERMINAL_VELOCITY);
    }

    @Override
    public void collect() {
        this.isCollected = true;
        setCentreX(OFFSCREEN);
        setCentreY(OFFSCREEN);
    }

    @Override
    public void display() {
        if (!isCollected) {
           getSprite().draw(getCentreX(), getCentreY());
        }
    }
}
