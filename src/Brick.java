import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Brick extends DisplayObject{
    public int strength;
    public Bonuses bonuses;



    @JsonIgnoreProperties(ignoreUnknown = true)
    public Brick (int x1, int y1,  int x2, int y2, int strength, int R, int G, int B, boolean isMoving)  {
        this.classType = 3;
        this.type = Type.BRICK;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.R = R;
        this.G = G;
        this.B = B;
        this.strength = strength;
        this.isMoving = isMoving;
        this.isVisible = true;
        eventManager = new EventManager();
        bonuses = new Bonuses();
        eventManager.registerEventHandler(CollisionEvent.class, new CollisionEventHandler());
    }
    @Override
    public void move() {

    }
    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(R, G, B));
        g.fillRect(x1, y1, x2 - x1, y2 - y1);
        g.setColor(Color.BLACK);
        g.drawRect(x1, y1, x2 - x1, y2 - y1);
    }

    @Override
    public void saveComponentData(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            writer.println(getClass().getName());
            writer.println(x1 + "," + y1 + "," + x2 + "," + y2 + "," + strength + "," + R + "," + G + "," + B + "," + isVisible);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readComponentData(String dataComponent) {
        String[] dataArray = dataComponent.split(",");
        this.x1 = Integer.parseInt(dataArray[0]);
        this.y1 = Integer.parseInt(dataArray[1]);
        this.x2 = Integer.parseInt(dataArray[2]);
        this.y2 = Integer.parseInt(dataArray[3]);
        this.strength = Integer.parseInt(dataArray[4]);
        this.R = Integer.parseInt(dataArray[5]);
        this.G = Integer.parseInt(dataArray[6]);
        this.B = Integer.parseInt(dataArray[7]);
        this.isVisible = Boolean.parseBoolean(dataArray[8]);
    }

    public void decreaseStrength()
    {
        strength -= 1;
        switch (strength)
        {
            case 4 :
                this.R = 68;
                this.G = 7;
                this.B = 77;
                break;
            case 3 :
                this.R = 96;
                this.G = 41;
                this.B = 96;
                break;
            case 2 :
                this.R = 146;
                this.G = 91;
                this.B = 148;
                break;
            case 1 :
                this.R = 194;
                this.G = 142;
                this.B = 200;
                break;
            case 0 :
                this.isVisible = false;
                BrickDestructionEvent event = new BrickDestructionEvent(this);
                eventManager.triggerEvent(event);
                break;
        }
    }

    private class CollisionEventHandler implements EventHandler<CollisionEvent> {
        @Override
        public void handle(CollisionEvent event) {
            decreaseStrength();
        }
    }
}
