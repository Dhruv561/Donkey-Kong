import bagel.*;

public class Ladder extends GameObject {
    private final static double GRAVITY = 0.25;
    public static final double LADDER_TERMINAL_VELOCITY = 5.0;

    public Ladder(double centreX, double centreY) {
        super(centreX, centreY, new Image("res/ladder.png"), GRAVITY, LADDER_TERMINAL_VELOCITY);
    }

    public void update(Platform[] platforms) {
        fallToPlatform(platforms);
        display();
    }
}
