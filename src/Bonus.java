import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Bonus extends DisplayObject{
    public int speed;
    public int points;
    public int bonusType;

    public Bonus (int x1, int y1, int x2, int y2, int speed, boolean isMoving){
        this.classType = 4;
        this.type = Type.BONUS;
        this.speed = speed;
        this.isMoving = isMoving;
        this.isVisible = false;
        this.points = ((int)(Math.random() * 10) + 1) * 10;
        this.bonusType = generateRandomNumber();
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        eventManager = new EventManager();
        eventManager.registerEventHandler(CollisionEvent.class, new CollisionEventHandler());
    }

    public  Bonus() {
        eventManager = new EventManager();
        eventManager.registerEventHandler(CollisionEvent.class, new CollisionEventHandler());
    }
    public int generateRandomNumber() {
        Random random = new Random();
        int randomNumber = random.nextInt(10);

        if (randomNumber <= 5) {
            return 5;
        } else {
            switch (randomNumber){
                case 6:
                    return 2;
                case 7:
                    return  3;
                case 8:
                    return 4;
                case 9:
                    return 5;
            }
        }
        return randomNumber;
    }

    @Override
    public void move() {
        y1 += speed;
        y2 += speed;
    }

    @Override
    public void saveComponentData(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            writer.println(getClass().getName());
            writer.println(x1 + "," + y1 + "," + x2 + "," + y2 + "," + speed + "," + points + "," + isVisible + "," + isMoving + "," + bonusType);
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
        this.speed = Integer.parseInt(dataArray[4]);
        this.points = Integer.parseInt(dataArray[5]);
        this.isVisible = Boolean.parseBoolean(dataArray[6]);
        this.isMoving = Boolean.parseBoolean(dataArray[7]);
        this.bonusType = Integer.parseInt(dataArray[8]);
    }

    @Override
    public void draw(Graphics g) {
        Color color = null;
        String text = "";

        int rectHeight = y2 - y1;
        int rectWidth = x2 - x1;
        Font font = new Font("Verdana", Font.BOLD, (int)(rectHeight * 0.9));
        g.setFont(font);
        switch (bonusType){
            case 1:
                text = "";
                break;
            case 2:
                text = "L";
                color = Color.ORANGE;
                break;
            case 3:
                text = "*2";
                color = Color.BLUE;
                break;
            case 4:
                text = "%2";
                color = Color.RED;
                break;
            case 5:
                text = "S";
                color = Color.GREEN;
                break;
        }

        g.setColor(Color.WHITE);
        FontMetrics fontMetrics = g.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(text);
        int textX = x1 + (rectWidth - textWidth) / 2;
        int textY = y1 + rectHeight - (int)(0.5 * fontMetrics.getDescent());
        g.drawString("+"+String.valueOf(points), textX, textY);
        g.setColor(color);
        g.drawString(text, textX + textWidth*2, textY - fontMetrics.getAscent());
    }

    private class CollisionEventHandler implements EventHandler<CollisionEvent> {
        @Override
        public void handle(CollisionEvent event) {
            isVisible = false;
            isMoving = false;
        }
    }
}
