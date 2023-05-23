import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DisplayAll {
    public List<DisplayObject> displayObjects = new ArrayList<>();
    public DisplayAll(Balls balls, Platforms platforms, Bricks bricks) {
        displayObjects.addAll(balls.balls);
        displayObjects.addAll(platforms.platforms);
        displayObjects.addAll(bricks.bricks);
    }

    public void moveObjects() {
        for (DisplayObject object : displayObjects) {
            if (object.isMoving && object.isVisible) {
                object.move();
            }
        }
    }

    public void drawObjects(Graphics g) {
        for (DisplayObject object : displayObjects) {
            if (object.isVisible) {
                object.draw(g);
            }
        }
    }

    public void checkCollisions() {
        for (DisplayObject object1 : displayObjects) {
            if (object1.isMoving && object1.isVisible) {
                for (DisplayObject object2 : displayObjects) {
                    if (object1.equals(object2) || !object2.isVisible) {
                        continue;
                    }
                    if (object1.checkCollisions(object2)) {
                        if (object1.type == Type.BALL) {
                            ((Ball) object1).changeDirection(object2);
                        }
                        break;
                    }
                }
            }
        }
    }

}
