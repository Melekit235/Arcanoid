import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;

public class Player {
    public static Statistic statistics;
    public static boolean isGameFailed = false;
    public Player(){
        statistics = new Statistic();
    }

    public int getLives() {
        return statistics.lives;
    }

    public void fail() {
        statistics.lives--;
        TableRecords.update();
        if (statistics.lives <= 0) {
            Game.pause();
        }
        else {
            Balls balls = new Balls();
            Platforms platforms = new Platforms();
            Game.gameField.allObjects.displayObjects.set(0, balls.balls.get(0));
            Game.gameField.allObjects.displayObjects.set(1, platforms.platforms.get(0));
            Game.gameField.platforms = platforms;
            Game.gameField.balls = balls;
            isGameFailed = true;
        }
    }
    public static String getPlayerStatistic(){
        return "        Name: " + statistics.name + "         Score: " + Integer.toString(statistics.score) + "         Lives: " + Integer.toString(statistics.lives);
    }
}
