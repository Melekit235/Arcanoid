import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bricks {
    public static List<Brick> bricks;

    public Bricks () {
        int Width = Game.WIDTH;
        int Height = Game.HEIGHT;
        bricks = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            bricks.add(new Brick(i * (Width / 11), (int) (Height * 0.15), (i + 1) * (Width / 11), (int) (Height * 0.18), 5,45,0,45,false));
            bricks.add(new Brick(i * (Width / 11), (int) (Height * 0.18), (i + 1) * (Width / 11), (int) (Height * 0.21), 4,68,7,77,false));
            bricks.add(new Brick(i * (Width / 11), (int) (Height * 0.21), (i + 1) * (Width / 11), (int) (Height * 0.24), 3,96, 41, 96,false));
            bricks.add(new Brick(i * (Width / 11), (int) (Height * 0.24), (i + 1) * (Width / 11), (int) (Height * 0.27), 2,146,91,148,false));
            bricks.add(new Brick(i * (Width / 11), (int) (Height * 0.27), (i + 1) * (Width / 11), (int) (Height * 0.3), 1,194,142,200,false));
        }
    }

    public static boolean isWin() {
        boolean result = true;
        for (Brick brick : bricks) {
            if (brick.isVisible) {
                result = false;
                break;
            }
        }
        return result;
    }

    public static void repaintBricks(){
        int Width = Game.WIDTH;
        int Height = Game.HEIGHT;
        int index = 0;
        for (int i = 1; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                bricks.get(index).x1 = i * (Width / 11);
                bricks.get(index).y1 = (int) (Height * (0.15 + (j * 0.03)));
                bricks.get(index).x2 = (i + 1) * (Width / 11);
                bricks.get(index).y2 = (int) (Height * (0.18 + (j * 0.03)));
                index++;
            }
        }
    }
}
