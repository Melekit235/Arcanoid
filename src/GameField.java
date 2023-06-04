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

            int x1 = Platforms.platforms.get(0).x1;
            int x2 = Platforms.platforms.get(0).x2;
            int halfWidth = (x2 - x1) / 2;
            switch (event.bonus.bonusType){
                case 2:
                    Player.statistics.lives += 1;
                    break;
                case 3:

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
                    break;
                case 4:
                    Platforms.platforms.get(0).x2 -= halfWidth;
                    Platforms.platforms.get(0).x1 += halfWidth;
                    timer = new Timer(5000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Platforms.platforms.get(0).x2 += halfWidth;
                            Platforms.platforms.get(0).x1 -= halfWidth;
                            timer.stop();
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                    if (Platforms.platforms.get(0).x2 > Game.WIDTH) {
                        Platforms.platforms.get(0).x2 = Game.WIDTH;
                        Platforms.platforms.get(0).x1 = Platforms.platforms.get(0).x2 - 4 * halfWidth;

                    }
                    break;
                case 5:
                    Settings.speedRatio += 0.001;
                    timer = new Timer(5000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Settings.speedRatio -= 0.001;
                            timer.stop();
                        }
                    });
                    break;
            }
            Player.statistics.score += event.bonus.points;
            TableRecords.update();
        }
    }
}
