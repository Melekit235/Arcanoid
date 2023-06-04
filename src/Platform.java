import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Platform extends DisplayObject {
    public int speed;
    public boolean moveLeft;
    public boolean moveRight;
    public Platform(int x1, int y1, int x2, int y2, int R, int G, int B, int speed, boolean isMoving) {
        this.classType = 2;
        type = Type.PLATFORM;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.R = R;
        this.G = G;
        this.B = B;
        this.speed = speed;
        this.isMoving = isMoving;
        this.isVisible = true;
        eventManager = new EventManager();
        eventManager.registerEventHandler(CollisionEvent.class, new CollisionEventHandler());
    }
    public Platform(){
        eventManager = new EventManager();
        eventManager.registerEventHandler(CollisionEvent.class, new CollisionEventHandler());
    }
    @Override
    public void move() {
        if (moveLeft || moveRight) {
            if (moveLeft) {
                x1 = (x1 - speed);
                x2 = (x2 - speed);
            }
            else {
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
        g.setColor(new Color(R, G, B));
        g.fillRect(x1, y1, x2 - x1, y2 - y1);
        g.setColor(Color.BLACK);
        g.drawRect(x1, y1, x2 - x1, y2 - y1);
    }

    @Override
    public void saveComponentData(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            writer.println(getClass().getName());
            writer.println(x1 + "," + y1 + "," + x2 + "," + y2 + "," + R + "," + G + "," + B + "," + speed);
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
        this.R = Integer.parseInt(dataArray[4]);
        this.G = Integer.parseInt(dataArray[5]);
        this.B = Integer.parseInt(dataArray[6]);
        this.speed = Integer.parseInt(dataArray[7]);
    }

    private class CollisionEventHandler implements EventHandler<CollisionEvent> {
        @Override
        public void handle(CollisionEvent event) {
        }
    }


}
