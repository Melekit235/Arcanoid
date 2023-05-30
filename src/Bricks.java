import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bricks {
    public static List<Brick> bricks;

    public Bricks () {
        bricks = new ArrayList<>();
        int Width = Game.WIDTH;
        int Height = Game.HEIGHT;
        for (int i = 1; i < 11; i++) {
            bricks.add(new Brick(i * (Width / 12), (int) (Height * 0.2), (i + 1) * (Width / 12), (int) (Height * 0.24), 6, new Color(128, 0, 128), false));
            bricks.add(new Brick(i * (Width / 12), (int) (Height * 0.24), (i + 1) * (Width / 12), (int) (Height * 0.28), 5, new Color(0, 0, 255), false));
            bricks.add(new Brick(i * (Width / 12), (int) (Height * 0.28), (i + 1) * (Width / 12), (int) (Height * 0.32), 4, new Color(0, 255, 0), false));
            bricks.add(new Brick(i * (Width / 12), (int) (Height * 0.32), (i + 1) * (Width / 12), (int) (Height * 0.36), 3, new Color(255, 255, 0), false));
            bricks.add(new Brick(i * (Width / 12), (int) (Height * 0.36), (i + 1) * (Width / 12), (int) (Height * 0.4), 2, new Color(255, 165, 0), false));
            bricks.add(new Brick(i * (Width / 12), (int) (Height * 0.4), (i + 1) * (Width / 12), (int) (Height * 0.44), 1, new Color(255, 0, 0), false));
        }
    }

    public static void repaintBricks(){
        int Width = Game.WIDTH;
        int Height = Game.HEIGHT;
        int index = 0;
        for (int i = 1; i < 11; i++) {
            for (int j = 0; j < 6; j++) {
                bricks.get(index).x1 = i * (Width / 12);
                bricks.get(index).y1 = (int) (Height * (0.2 + (j * 0.04)));
                bricks.get(index).x2 = (i + 1) * (Width / 12);
                bricks.get(index).y2 = (int) (Height * (0.24 + (j * 0.04)));
                index++;
            }
        }
    }
}

