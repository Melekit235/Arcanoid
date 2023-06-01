import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Bonus extends DisplayObject{
    public int speed;
    public int points;
    public int num;
    public int bonusType;
    private static final Font font = new Font("Arial", Font.BOLD, 30);

    public Bonus (int x1, int y1, int x2, int y2, int speed, boolean isMoving){
        this.classType = 4;
        this.type = Type.BONUS;
        this.R = 255;
        this.G = 0;
        this.B = 0;
        this.speed = speed;
        this.isMoving = isMoving;
        this.isVisible = false;
        this.points = ((int)(Math.random() * 10) + 1) * 10;
        this.num = generateRandomNumber();
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        eventManager = new EventManager();
        eventManager.registerEventHandler(CollisionEvent.class, new CollisionEventHandler());
    }

    public  Bonus() {

    }
    public int generateRandomNumber() {
        Random random = new Random();
        int randomNumber = 0;
        int probability = random.nextInt(15);
        if (probability == 0) {
            this.bonusType = 1;
        } else if (probability == 13) {
            this.bonusType = 2;
        } else {
            this.bonusType = 3;
            while (true) {
                probability = random.nextInt(10);
                if (probability == 0) {
                    randomNumber = (random.nextInt(11) - 10) * 10;
                } else {
                    randomNumber = (random.nextInt(10) + 1) * 10;
                }
                if (randomNumber != 0)
                    break;
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
            writer.println(x1 + "," + y1 + "," + x2 + "," + y2 + "," + speed + "," + num + "," + isVisible + "," + isMoving);
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
        this.num = Integer.parseInt(dataArray[5]);
        this.isVisible = Boolean.parseBoolean(dataArray[6]);
        this.isMoving = Boolean.parseBoolean(dataArray[7]);

    }

    @Override
    public void draw(Graphics g) {
        Color color;
        String text = "";
        if (bonusType == 3)
            text = num > 0 ? "+" + num : "" + num;
        else if (bonusType == 1)
            text = "X2";
        else if (bonusType == 2) {
            text = "W";
        }
        int rectHeight = y2 - y1;
        int rectWidth = x2 - x1;
        Font font = new Font("Verdana", Font.BOLD, (int)(rectHeight * 0.9));
        g.setFont(font);
        color = num > 0 ? Color.WHITE : Color.RED;
        if (text == "X2")
            color = Color.BLUE;
        else if (text == "W") {
            color = new Color(0, 100, 0);
        }
        g.setColor(color);
        FontMetrics fontMetrics = g.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(text);
        int textX = x1 + (rectWidth - textWidth) / 2;
        int textY = y1 + rectHeight - (int)(0.5 * fontMetrics.getDescent());
        g.drawString(text, textX, textY);}

    private class CollisionEventHandler implements EventHandler<CollisionEvent> {
        @Override
        public void handle(CollisionEvent event) {
            isVisible = false;
            isMoving = false;
        }
    }
}
