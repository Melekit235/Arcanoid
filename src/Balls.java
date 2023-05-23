import java.awt.*;
import java.util.ArrayList;

public class Balls {
    public ArrayList<Ball> balls;
    public Balls() {
        balls = new ArrayList<>();
        balls.add(new Ball(315,315,7,2,(float)(Math.PI/2*0.5), Color.WHITE, true));
    }
}
