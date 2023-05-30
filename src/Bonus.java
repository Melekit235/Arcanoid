import java.awt.*;

public class Bonus extends DisplayObject{
    public int speed;
    public int num;

    private static final Font font = new Font("Arial", Font.BOLD, 30);

    public Bonus (int x1, int y1, int x2, int y2, int speed, Color color, boolean isMoving) {
        this.type = Type.BONUS;
        this.color = color;
        this.speed = speed;
        this.isMoving = isMoving;
        this.isVisible = false;
        this.num = ((int)(Math.random() * 10) + 1) * 10;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    @Override
    public void move() {
        y1 += speed;
        y2 += speed;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString("+" + num, x1 + (x2 - x1) / 2, y1 + (y2 - y1) / 2);
    }
}
