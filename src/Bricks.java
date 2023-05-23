import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bricks {
    public List<Brick> bricks = new ArrayList<>();

    public Bricks () {
        for (int i = 1; i < 11; i++) {
            bricks.add(new Brick(i*70, 110,i*70+70, 130, 6, new Color(128,0,128), false));
            bricks.add(new Brick(i*70, 130,i*70+70, 150, 5, new Color(0,0,255), false));
            bricks.add(new Brick(i*70, 150,i*70+70, 170, 4, new Color(0,255,0),  false));
            bricks.add(new Brick(i*70, 170,i*70+70, 190, 3, new Color(255,255,0),  false));
            bricks.add(new Brick(i*70, 190,i*70+70, 210, 2, new Color(255,165,0),  false));
            bricks.add(new Brick(i*70, 210,i*70+70, 230, 1, new Color(255,0,0),  false));
        }
    }
}
