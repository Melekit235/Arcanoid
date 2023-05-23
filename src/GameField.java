public class GameField {
    public DisplayAll allObjects;
    public Balls balls;
    public Platforms platforms;
    public Bricks bricks;

    public GameField() {
        balls = new Balls();
        platforms = new Platforms();
        bricks = new Bricks();
        allObjects = new DisplayAll(balls, platforms, bricks);
    }
}
