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

        allObjects = new DisplayAll(balls, platforms, bricks, bonuses);
    }
}
