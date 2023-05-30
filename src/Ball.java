import java.awt.*;
import java.io.PrintStream;
import java.util.Timer;

public class Ball extends DisplayObject {
    public int radius;
    public int speed;
    private float direction;
    private float dx;
    private float dy;
    private boolean fromWall;
    private static boolean flag;



    public Ball (int x, int y, int radius, int speed, float direction, Color color, boolean isMoving) {
        this.type = Type.BALL;
        this.radius = radius;
        this.color = color;
        this.speed = speed;
        this.direction = direction;
        this.isMoving = isMoving;
        this.isVisible = true;
        this.x1 = x;
        this.x2 = x + 2 * radius;
        this.y1 = y;
        this.y2 = y + 2 * radius;
        this.eventManager = new EventManager();
        eventManager.registerEventHandler(CollisionEvent.class, new CollisionEventHandler());
    }

    @Override
    public void move() {
        if (x1 <= 0) {
            direction = (float)(Math.PI) - direction;
        } else {
            if (x2 >= Game.WIDTH) {
                direction = (float)(Math.PI) - direction;
            }
            else {
                if (y1 <= 35) {
                    direction = -direction;
                }
            }
        }

        dx = (float) Math.cos(direction) * speed;
        dy = (float) Math.sin(direction) * speed;
        x1 = x1 + (int)dx;
        x2 = x2 + (int)dx;
        y1 = y1 + (int)dy;
        y2 = y2 + (int)dy;

        fromWall = false;
        if (x1 >= Game.WIDTH - 2 * radius) {
            x1 = Game.WIDTH - 2 * radius;
            x2 = Game.WIDTH;
            fromWall = true;
        }
        if (x1 <= 0) {
            x1 = 0;
            x2 = 2 * radius;
            fromWall = true;
        }
        if (y1 <= 35) {
            y1 = 35;
            y2 = 35 + 2 * radius;
            fromWall = true;
        }
        if (y1 >= Game.HEIGHT) {
            Game.player.fail();
        }
        if (fromWall) {
            flag = fromWall;
        }
    }

    public void changeDirection(DisplayObject object) {
        if (object.type != Type.BONUS) {
            if ((y2 >= object.y1) && (y1 < object.y1) && (y2 < object.y2) && ((x2 >= object.x1) && (x1 <= object.x2)) && (dy > 0)) {
                y2 = object.y1;
                y1 = y2 - 2 * radius;
                direction = -direction;
            } else if ((y1 <= object.y2) && (y2 > object.y2) && (((x2 >= object.x1) && (x1 <= object.x2)) && (dy < 0))) {
                y1 = object.y2;
                y2 = y1 + 2 * radius;
                direction = -direction;
            } else if ((x1 <= object.x2) && (x1 > object.x1) && ((y2 >= object.y1) && (y1 <= object.y2)) && (dx < 0)) {
                x1 = object.x2;
                x2 = x1 + 2 * radius;
                direction = (float) Math.PI - direction;
            } else if ((x2 >= object.x1) && (y2 < object.y2) && ((y2 >= object.y1) || (y1 <= object.y2)) && (dx > 0)) {
                x2 = object.x1;
                x1 = x2 - 2 * radius;
                direction = (float) Math.PI - direction;
            }

        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x1, y1, 2 * radius, 2 * radius);
        g.setColor(Color.BLACK);
        g.drawOval(x1, y1, 2 * radius, 2 * radius);
    }

    private class CollisionEventHandler implements EventHandler<CollisionEvent> {
        @Override
        public void handle(CollisionEvent event) {
            changeDirection(event.obj2);
        }
    }

}
