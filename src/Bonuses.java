import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bonuses {
    public static List<Bonus> bonuses;

    public Bonuses () {
        int Width = Game.WIDTH;
        int Height = Game.HEIGHT;
        bonuses = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            bonuses.add(new Bonus(i * (Width / 11), (int) (Height * 0.15), (i + 1) * (Width / 11), (int) (Height * 0.18), (int)((Game.HEIGHT) * 0.0045f),false));
            bonuses.add(new Bonus(i * (Width / 11), (int) (Height * 0.18), (i + 1) * (Width / 11), (int) (Height * 0.21), (int)((Game.HEIGHT) * 0.0045f),false));
            bonuses.add(new Bonus(i * (Width / 11), (int) (Height * 0.21), (i + 1) * (Width / 11), (int) (Height * 0.24), (int)((Game.HEIGHT) * 0.0045f),false));
            bonuses.add(new Bonus(i * (Width / 11), (int) (Height * 0.24), (i + 1) * (Width / 11), (int) (Height * 0.27), (int)((Game.HEIGHT) * 0.0045f),false));
            bonuses.add(new Bonus(i * (Width / 11), (int) (Height * 0.27), (i + 1) * (Width / 11), (int) (Height * 0.3), (int)((Game.HEIGHT) * 0.0045f),false));
        }
    }

    public static void resetVisibility () {
        for (Bonus bonus : bonuses) {
            bonus.isVisible = false;
        }
    }
    public static void repaintBonuses(){
        int Width = Game.WIDTH;
        int Height = Game.HEIGHT;
        int index = 0;
        for (int i = 1; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                bonuses.get(index).x1 = i * (Width / 11);
                bonuses.get(index).y1 = (int) (Height * (0.15 + (j * 0.03)));
                bonuses.get(index).x2 = (i + 1) * (Width / 11);
                bonuses.get(index).y2 = (int) (Height * (0.18 + (j * 0.03)));
                index++;
            }
        }
    }
}
