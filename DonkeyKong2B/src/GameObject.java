import bagel.*;
import bagel.util.*;
import java.util.Properties;

public abstract class GameObject implements Collidable, Fallable {
    private double centreX;
    private double centreY;
    private double height;
    private double width;
    private Image sprite;

    public double getCentreX() {
        return this.centreX;
    }

    public void setCentreX(double centreX) {
        this.centreX = centreX;
    }

    public double getCentreY() {
        return this.centreY;
    }

    public void setCentreY(double centreY) {
        this.centreY = centreY;
    }

    public double getWidth() {
        return this.width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return this.height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getLeftX() {
        return this.centreX - (this.width / 2);
    }

    public double getRightX() {
        return this.centreX + (this.width / 2);
    }

    public double getTopY() {
        return this.centreY - (this.height / 2);
    }

    public double getBottomY() {
        return this.centreY + (this.height / 2);
    }

    public Rectangle getBoundingBox() {
        return sprite.getBoundingBoxAt(new Point(centreX, centreY));
    }

    public void display() {
        sprite.draw(centreX, centreY);
    }

    public boolean isTouching(GameObject object) {
        return getBoundingBox().intersects(object.getBoundingBox());
    }

}
