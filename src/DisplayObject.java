import java.awt.*;

public abstract class DisplayObject {

    public int x1, y1, x2, y2;
    public Color color;
    public boolean isVisible;
    public boolean isMoving;
    Type type;

    public abstract void move();
    public abstract void draw(Graphics g);

    public boolean checkCollisions(DisplayObject object) {
        if (y2 >= object.y1 && y1 <= object.y2) {
            if (x2 >= object.x1 && x1 <= object.x2) {
                return true;
            }
        }
        return false;
    }
}
