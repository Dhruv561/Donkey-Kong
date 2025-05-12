import bagel.*;

public class Barrel extends GameObject {
    private boolean isDestroyed = false;
    private final static double GRAVITY = 0.4;
    public static final double BARREL_TERMINAL_VELOCITY = 5.0;
    private final static int offscreen = -100;

    public Barrel(double centreX, double centreY) {
        super(centreX, centreY, new Image("res/barrel.png") , GRAVITY, BARREL_TERMINAL_VELOCITY);
    }

    @Override
    public void display() {
        if (!isDestroyed) {
            getSprite().draw(getCentreX(), getCentreY());
        }
    }

    public void destroy() {
        this.isDestroyed = true;
        setCentreX(offscreen);
        setCentreY(offscreen);
    }

    public void update(Platform[] platforms) {
        fallToPlatform(platforms);
        display();
    }
}
