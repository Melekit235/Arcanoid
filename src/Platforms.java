import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Platforms {
    public List<Platform> platforms;
    public Platforms() {
        platforms = new ArrayList<>();
        platforms.add(new Platform(340, 620, 460, 640, new Color(106, 114,114), 2, true ));
    }
}
