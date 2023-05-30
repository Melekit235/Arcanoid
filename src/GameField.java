public class GameField {

    public DisplayAll allObjects;
    public Balls balls;
    public Platforms platforms;
    public Bricks bricks;
    public Bonuses bonuses;
    public Menu menu;
    public Settings settings;
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

            Player.statistics.score += event.bonus.points;
            TableRecords.update();
        }
    }
}
