import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameField {

    public DisplayAll allObjects;
    public Balls balls;
    public Platforms platforms;
    public Bricks bricks;
    public Bonuses bonuses;
    public Menu menu;
    public Settings settings;
    private Timer timer;
    public GameField() {
        settings = new Settings();
        platforms = new Platforms();
        balls = new Balls();
        bricks = new Bricks();
        bonuses = new Bonuses();
        menu = new Menu();

        setBrickDestructionEvent(new BrickDestructionEventHandler());
        setBonusCatchEvent(new BonusCatchEventHandler());

        allObjects = new DisplayAll(balls, platforms, bricks, bonuses);
    }
    public void setBrickDestructionEvent (EventHandler<BrickDestructionEvent> handler) {
        for (Brick brick : bricks.bricks){
            brick.eventManager.registerEventHandler(BrickDestructionEvent.class, handler);
        }
    }
    private class BrickDestructionEventHandler implements EventHandler<BrickDestructionEvent> {
        @Override
        public void handle(BrickDestructionEvent event) {

            int num = Bricks.bricks.indexOf(event.brick);
            bonuses.bonuses.get(num).isMoving = true;
            bonuses.bonuses.get(num).isVisible = true;
            Player.statistics.score += 10;
            TableRecords.update();
        }
    }

    public void setBonusCatchEvent (EventHandler<BonusCatchEvent> handler) {
        for (Bonus bonus: bonuses.bonuses){
            bonus.eventManager.registerEventHandler(BonusCatchEvent.class, handler);
        }
    }
    private class BonusCatchEventHandler implements EventHandler<BonusCatchEvent> {
        @Override
        public void handle(BonusCatchEvent event) {

            int num = (event.bonus).num;
            if ((event.bonus).bonusType == 1) {
                int x1 = Platforms.platforms.get(0).x1;
                int x2 = Platforms.platforms.get(0).x2;
                int halfWidth = (x2 - x1) / 2;
                Platforms.platforms.get(0).x2 += halfWidth;
                Platforms.platforms.get(0).x1 -= halfWidth;
                timer = new Timer(5000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Platforms.platforms.get(0).x2 -= halfWidth;
                        Platforms.platforms.get(0).x1 += halfWidth;
                        timer.stop();
                    }
                });
                timer.setRepeats(false);
                timer.start();
                if (Platforms.platforms.get(0).x2 > Game.WIDTH) {
                    Platforms.platforms.get(0).x2 = Game.WIDTH;
                    Platforms.platforms.get(0).x1 = Platforms.platforms.get(0).x2 - 4 * halfWidth;

                }
            } else if ((event.bonus).bonusType == 2) {
                int width = Platforms.platforms.get(0).x2 - Platforms.platforms.get(0).x1;
                Platforms.platforms.get(0).x1 = 0;
                Platforms.platforms.get(0).x2 = Game.WIDTH;
                float speedRatio = Settings.speedRatio;
                Balls.balls.get(0).speed = (int) (Game.HEIGHT * 0.03f);
                timer = new Timer(5000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (Balls.balls.get(0).dx < 0) {
                            Platforms.platforms.get(0).x1 = Balls.balls.get(0).x1 - 10 * (Balls.balls.get(0).x2 - Balls.balls.get(0).x1);
                            Platforms.platforms.get(0).x2 = Platforms.platforms.get(0).x1 + width;
                        } else if (Balls.balls.get(0).dx > 0) {
                            Platforms.platforms.get(0).x1 = Balls.balls.get(0).x1 + 10 * (Balls.balls.get(0).x2 - Balls.balls.get(0).x1);
                            Platforms.platforms.get(0).x2 = Platforms.platforms.get(0).x1 + width;
                        }
                        Balls.balls.get(0).speed = (int) (Game.HEIGHT * speedRatio);
                        timer.stop();
                    }
                });
                timer.setRepeats(false);
                timer.start();
            }
            Player.statistics.score += num;
            TableRecords.update();
        }
    }
}
