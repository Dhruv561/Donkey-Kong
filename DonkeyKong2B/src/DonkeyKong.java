import bagel.*;

public class DonkeyKong extends Entity {
    private final static double GRAVITY = 0.4;
    private final static double DONKEY_TERMINAL_VELOCITY = 5;

    public DonkeyKong(double centreX, double centreY) {
        super(centreX, centreY, new Image("res/donkey_kong.png"), new Image("res/donkey_kong.png"), GRAVITY, DONKEY_TERMINAL_VELOCITY);
    }

    @Override
    public void moveLeft() {

    }

    @Override
    public void moveRight() {

    }
}
