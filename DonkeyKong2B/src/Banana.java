import bagel.*;

public class Banana extends GameObject {
    private boolean isDestroyed = false;
    private final static int BANANA_GRAVITY = 0;
    private final static int BANANA_TERMINAL_VELOCITY = 0;
    private final static double MOVEMENT_VELOCITY = 1.8;
    private final static int MAXIMUM_PIXELS = 300;

    public Banana(double centreX, double centreY) {
        super(centreX, centreY, new Image("res/banana.png"), BANANA_GRAVITY, BANANA_TERMINAL_VELOCITY);
    }


    public void update() {

    }
}
