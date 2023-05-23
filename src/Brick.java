import java.awt.*;

public class Brick extends DisplayObject{
    public int strength;
    public Bonuses bonuses;

    public Brick (int x1, int y1,  int x2, int y2, int strength, Color color, boolean isMoving) {
        this.type = Type.BRICK;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.color = color;
        this.strength = strength;
        this.isMoving = isMoving;
        this.isVisible = true;
    }
    @Override
    public void move() {

    }
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x1, y1, x2 - x1, y2 - y1);
        g.setColor(Color.BLACK);
        g.drawRect(x1, y1, x2 - x1, y2 - y1);
    }

    public void decreaseStrength()
    {
        strength -= 1;
        switch (strength)
        {
            case 5:
                this.color = new Color(0,0,255);
                break;
            case 4:
                this.color = new Color(0,255,0);
                break;
            case 3:
                this.color = new Color(255,255,0);
                break;
            case 2:
                this.color = new Color(255,165,0);
                break;
            case 1:
                this.color = new Color(255,0,0);
                break;
            case 0:
                this.isVisible = false;
                break;
        }
    }
}
