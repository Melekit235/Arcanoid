public class BrickDestructionEvent extends Event{
    public Brick brick;

    public BrickDestructionEvent(Brick brick) {
        this.brick = brick;
    }
}
