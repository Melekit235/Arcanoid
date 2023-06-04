import java.util.ArrayList;
import java.util.List;

public class Bricks {
    public static List<Brick> bricks;

    public Bricks () {
        int Width = Game.WIDTH;
        int Height = Game.HEIGHT;
        bricks = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            bricks.add(new Brick(i * (Width / 11), (int) (Height * 0.12), (i + 1) * (Width / 11), (int) (Height * 0.15), 6,255,0,0,false));
            bricks.add(new Brick(i * (Width / 11), (int) (Height * 0.15), (i + 1) * (Width / 11), (int) (Height * 0.18), 5,255,165,0,false));
            bricks.add(new Brick(i * (Width / 11), (int) (Height * 0.18), (i + 1) * (Width / 11), (int) (Height * 0.21), 4,255,255,0,false));
            bricks.add(new Brick(i * (Width / 11), (int) (Height * 0.21), (i + 1) * (Width / 11), (int) (Height * 0.24), 3,0, 255, 0,false));
            bricks.add(new Brick(i * (Width / 11), (int) (Height * 0.24), (i + 1) * (Width / 11), (int) (Height * 0.27), 2,0,0,255,false));
            bricks.add(new Brick(i * (Width / 11), (int) (Height * 0.27), (i + 1) * (Width / 11), (int) (Height * 0.3), 1,128,0,128,false));}
        for (int i = 0; i < 6; i++){
            bricks.get(i).strength = 9;
            bricks.get(i).R = 106;
            bricks.get(i).G = 114;
            bricks.get(i).B = 114;
        }
        for (int i = 53; i > 47; i--){
            bricks.get(i).strength = 9;
            bricks.get(i).R = 106;
            bricks.get(i).G = 114;
            bricks.get(i).B = 114;
        }
    }

    public static void repaintBricks(){
        int Width = Game.WIDTH;
        int Height = Game.HEIGHT;
        int index = 0;
        for (int i = 1; i < 10; i++) {
            for (int j = 0; j < 6; j++) {
                bricks.get(index).x1 = i * (Width / 11);
                bricks.get(index).y1 = (int) (Height * (0.15 + (j * 0.03)));
                bricks.get(index).x2 = (i + 1) * (Width / 11);
                bricks.get(index).y2 = (int) (Height * (0.18 + (j * 0.03)));
                index++;
            }
        }
    }
}
