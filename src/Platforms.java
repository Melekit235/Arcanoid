import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Platforms {
    public static List<Platform> platforms;
    public Platforms() {
        platforms = new ArrayList<>();
        int Width = Game.WIDTH;
        int Height = Game.HEIGHT;
        platforms.add(new Platform((int) (Width / 11 + Width * 0.5 - Width / 22), (int) (Height * 0.95), (int) (Width / 11 + Width * 0.56 + Width / 22), (int) (Height * 0.983), 106, 114,114, (int)((Game.HEIGHT) * Settings.speedRatio) + 2, true));
    }

    public static void repaintPlatform(){
        int Width = Game.WIDTH;
        int Height = Game.HEIGHT;
        platforms.get(0).x1 = (int) (Width / 11 + Width * 0.5 - (Width / 22));
        platforms.get(0).y1 = (int) (Height * 0.95);
        platforms.get(0).x2 = (int) (Width / 11 + Width * 0.56 + (Width / 22));
        platforms.get(0).y2 = (int) (Height * 0.983);
        platforms.get(0).speed = (int) (Height * Settings.speedRatio) + 2;
    }
}