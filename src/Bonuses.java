import java.awt.*;
import java.util.ArrayList;

public class Bonuses {
    public static ArrayList<Bonus> bonuses;
    public Bonuses() {
        bonuses = new ArrayList<>();
        int Width = Game.WIDTH;
        int Height = Game.HEIGHT;
        for (int i = 1; i < 11; i++) {
            bonuses.add(new Bonus(i * (Width / 12), (int) (Height * 0.2), (i + 1) * (Width / 12), (int) (Height * 0.24), 6, new Color(128, 0, 128), false));
            bonuses.add(new Bonus(i * (Width / 12), (int) (Height * 0.24), (i + 1) * (Width / 12), (int) (Height * 0.28), 5, new Color(0, 0, 255), false));
            bonuses.add(new Bonus(i * (Width / 12), (int) (Height * 0.28), (i + 1) * (Width / 12), (int) (Height * 0.32), 4, new Color(0, 255, 0), false));
            bonuses.add(new Bonus(i * (Width / 12), (int) (Height * 0.32), (i + 1) * (Width / 12), (int) (Height * 0.36), 3, new Color(255, 255, 0), false));
            bonuses.add(new Bonus(i * (Width / 12), (int) (Height * 0.36), (i + 1) * (Width / 12), (int) (Height * 0.4), 2, new Color(255, 165, 0), false));
            bonuses.add(new Bonus(i * (Width / 12), (int) (Height * 0.4), (i + 1) * (Width / 12), (int) (Height * 0.44), 1, new Color(255, 0, 0), false));
        }
    }

    public static void addBonus(int x1, int y1, int x2, int y2){
        bonuses.add(new Bonus(x1, y1, x2, y2,(int)((Game.HEIGHT) * Settings.speedRatio) + 2, new Color(106, 114, 114),true));
    }
}
