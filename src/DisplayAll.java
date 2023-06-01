import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class DisplayAll {
    public static List<DisplayObject> displayObjects;
    public static boolean isExpanding;
    private Timer timer;
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
                            int num = ((Bonus)object2).num;
                            if (((Bonus) object2).bonusType == 1) {
                                int x1 = object1.x1;
                                int x2 = object1.x2;
                                int halfWidth = (x2 - x1) / 2;
                                object1.x2 += halfWidth;
                                object1.x1 -= halfWidth;
                                isExpanding = true;
                                timer = new Timer(5000, new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        object1.x2 -= halfWidth;
                                        object1.x1 += halfWidth;
                                        isExpanding = false;
                                        timer.stop();
                                    }
                                });
                                timer.setRepeats(false);
                                timer.start();
                                if (object1.x2 > Game.WIDTH) {
                                    //isExpanding = true;
                                    object1.x2 = Game.WIDTH;
                                    object1.x1 = object1.x2 - 4 * halfWidth;

                                }
                            } else if (((Bonus) object2).bonusType == 2) {
                                int width = object1.x2 - object1.x1;
                                object1.x1 = 0;
                                object1.x2 = Game.WIDTH;
                                float speedRatio = Settings.speedRatio;
                                Balls.balls.get(0).speed = (int) (Game.HEIGHT * 0.03f);
                                timer = new Timer(5000, new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        if (Balls.balls.get(0).dx < 0) {
                                            object1.x1 = Balls.balls.get(0).x1 - 10 * (Balls.balls.get(0).x2 - Balls.balls.get(0).x1);
                                            object1.x2 = object1.x1 + width;
                                        } else if (Balls.balls.get(0).dx > 0) {
                                            object1.x1 = Balls.balls.get(0).x1 + 10 * (Balls.balls.get(0).x2 - Balls.balls.get(0).x1);
                                            object1.x2 = object1.x1 + width;
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
        // int i = 0;
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
