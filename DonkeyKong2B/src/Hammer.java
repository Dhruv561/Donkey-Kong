import bagel.*;
import bagel.util.*;

public class Hammer extends GameObject {
    private boolean isDestroyed = false;
    private final static double GRAVITY = 0;
    public static final double HAMMER_TERMINAL_VELOCITY = 0;

    public Hammer(double centreX, double centreY) {
        super(centreX, centreY, new Image("res/hammer.png") , GRAVITY, HAMMER_TERMINAL_VELOCITY);
    }

    @Override
    public void display() {
        if (!isDestroyed) {
            display();
        }
    }
}
