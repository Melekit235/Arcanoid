public class CollisionEvent extends Event {
    public DisplayObject obj1;
    public DisplayObject obj2;
    public CollisionEvent(DisplayObject obj1, DisplayObject obj2) {
        this.obj1 = obj1;
        this.obj2 = obj2;
    }
}
