import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;

public class AnotherMessageBox extends DisplayObject{

    public AnotherMessageBox(){

        this.isVisible = true;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Game.pause();
                //parentFrame.setVisible(false);
                //parentFrame.requestFocus();
            }
        });
    }
    @Override
    public void move() {

    }

    @Override
    public void draw(Graphics g) {
        String text = "Вы проиграли";
        Font font = new Font("Arial", Font.BOLD, 24);
        FontMetrics metrics = g.getFontMetrics(font);

        int x = (Game.WIDTH - metrics.stringWidth(text)) / 2;
        int y = ((Game.HEIGHT - metrics.getHeight()) / 2) + metrics.getAscent();

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(text, x, y);

        text = "ок";
        font = new Font("Arial", Font.BOLD, 72);
        metrics = g.getFontMetrics(font);

        x = (Game.WIDTH - metrics.stringWidth(text)) / 2;
        y = ((Game.HEIGHT - metrics.getHeight()*3) / 2) + metrics.getAscent();

        g.setFont(font);
        g.drawString(text, x, y);
    }

    @Override
    public void saveComponentData(String filename) {

    }

    @Override
    public void readComponentData(String dataComponent) {

    }
}
