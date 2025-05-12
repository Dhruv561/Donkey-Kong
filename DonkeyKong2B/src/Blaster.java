import bagel.*;

public class Blaster extends GameObject implements Collectable {
    private boolean isCollected = false;
    private final static int BLASTER_GRAVITY = 0;
    private final static int BLASTER_TERMINAL_VELOCITY = 0;

    public Blaster(double centreX, double centreY) {
        super(centreX, centreY, new Image("res/blaster.png"), BLASTER_GRAVITY, BLASTER_TERMINAL_VELOCITY);
    }

    @Override
    public void display() {
        if (!isCollected) {
            getSprite().draw(getCentreX(), getCentreY());
        }
    }

    @Override
    public void collect() {

    }
}
