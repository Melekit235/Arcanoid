import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DisplayAll {
    public static List<DisplayObject> displayObjects;

    public DisplayAll(Balls balls, Platforms platforms, Bricks bricks, Bonuses bonuses) {
        displayObjects = new ArrayList<>();
        displayObjects.addAll(balls.balls);
        displayObjects.addAll(platforms.platforms);
        displayObjects.addAll(bricks.bricks);
        displayObjects.addAll(bonuses.bonuses);
    }

    public void moveObjects() {
        for (DisplayObject object : displayObjects) {
            if (object.isMoving && object.isVisible) {
                object.move();
            }
        }
    }

    public void drawObjects(Graphics g) {
        for (DisplayObject object : displayObjects) {
            if (object.isVisible) {
                object.draw(g);
            }
        }
    }

    public void checkCollisions() throws InterruptedException {
        for (DisplayObject object1 : displayObjects) {
            if (object1.isMoving && object1.isVisible) {
                for (DisplayObject object2 : displayObjects) {
                    if (object1.equals(object2) || !object2.isVisible) {
                        continue;
                    }
                    if (object1.checkCollisions(object2)) {
                        if (object1.type == Type.BALL && object2.type != Type.BONUS) {
                            CollisionEvent event = new CollisionEvent(object1, object2);
                            object1.eventManager.triggerEvent(event);
                            object2.eventManager.triggerEvent(event);
                        }
                        if (object1.type == Type.PLATFORM && object2.type == Type.BONUS) {
                            BonusCatchEvent eventBonus = new BonusCatchEvent((Bonus) object2);
                            object2.eventManager.triggerEvent(eventBonus);
                            CollisionEvent eventCollision = new CollisionEvent(object1, object2);
                            object1.eventManager.triggerEvent(eventCollision);
                            object2.eventManager.triggerEvent(eventCollision);
                        }
                        break;
                    }
                }
            }
        }
    }

    public void readDataComponentFromJSON(JsonNode rootNode) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        displayObjects = new ArrayList<>();
        JsonNode objectsNode = rootNode.get("displayObjects");
        Balls.balls = new ArrayList<>();
        Platforms.platforms = new ArrayList<>();
        Bricks.bricks = new ArrayList<>();
        Bonuses.bonuses = new ArrayList<>();

        for (JsonNode objectNode : objectsNode) {
            int classType = objectNode.get("classType").asInt();
            switch (classType) {
                case 1 :
                    displayObjects.add(mapper.readValue(objectNode.toString(), Ball.class));
                    displayObjects.get(displayObjects.size() - 1).type = Type.BALL;
                    Balls.balls.add((Ball) displayObjects.get(displayObjects.size() - 1));
                    break;
                case 2 :
                    displayObjects.add(mapper.readValue(objectNode.toString(), Platform.class));
                    displayObjects.get(displayObjects.size() - 1).type = Type.PLATFORM;
                    Platforms.platforms.add((Platform) displayObjects.get(displayObjects.size() - 1));
                    break;
                case 3 :
                    displayObjects.add(mapper.readValue(objectNode.toString(), Brick.class));
                    displayObjects.get(displayObjects.size() - 1).type = Type.BRICK;
                    Bricks.bricks.add((Brick)displayObjects.get(displayObjects.size() - 1));
                    break;
                case 4 :
                    displayObjects.add(mapper.readValue(objectNode.toString(), Bonus.class));
                    displayObjects.get(displayObjects.size() - 1).type = Type.BONUS;
                    Bonuses.bonuses.add((Bonus)displayObjects.get(displayObjects.size() - 1));
            }
        }
        Game.gameField.platforms.platforms.set(0, (Platform)displayObjects.get(1));
        Game.gameField.balls.balls.set(0, (Ball)displayObjects.get(0));
    }

}
