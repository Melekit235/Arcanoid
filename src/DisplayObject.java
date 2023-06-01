import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;

public abstract class DisplayObject {
    public int x1, y1, x2, y2;
    public int R;
    public int G;
    public int B;
    public boolean isVisible;
    public boolean isMoving;

    public EventManager eventManager;
    public int classType;
    Type type;

    public abstract void move();
    public abstract void draw(Graphics g);
    public abstract void saveComponentData(String filename);
    public abstract void readComponentData(String dataComponent);

    public boolean checkCollisions(DisplayObject object) {
        if (y2 >= object.y1 && y1 <= object.y2) {
            if (x2 >= object.x1 && x1 <= object.x2) {
                return true;
            }
        }
        return false;
    }
}

