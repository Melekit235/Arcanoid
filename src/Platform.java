import javax.swing.*;
import java.awt.*;


public class Platform extends DisplayObject {
    public int speed;
    public boolean moveLeft;
    public boolean moveRight;
    public Platform(int x1, int y1, int x2, int y2, Color color, int speed, boolean isMoving)
    {
        type = Type.PLATFORM;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.color = color;
        this.speed = speed;
        this.isMoving = isMoving;
        this.isVisible = true;
    }
    @Override
    public void move() {
        if (moveLeft || moveRight) {
            if (moveLeft) {
                x1 = (x1 - speed);
                x2 = (x2 - speed);
            }
            else if (moveRight) {
                x1 = (x1 + speed);
                x2 = (x2 + speed);
            }

            if (x1 < 0) {
                x2 = (x2 - x1);
                x1 = 0;
            }
            else if (x2 > Game.WIDTH) {
                x1 = Game.WIDTH - (x2 - x1);
                x2 = Game.WIDTH;
            }
       }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x1, y1, x2 - x1, y2 - y1);
        g.setColor(Color.BLACK);
        g.drawRect(x1, y1, x2 - x1, y2 - y1);
    }




}
