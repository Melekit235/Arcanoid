import com.fasterxml.jackson.databind.JsonNode;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Statistic {
    public int score;
    public int lives;
    public String name;
    public int level;
    public String complexity;

    public Statistic() {
        score = 0;
        lives = 3;
        name = "Игрок 1";
        level = 1;
        //complexity = SettingsComponents.settingsItems.get(0).getText();
    }

    public void saveComponentData(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            writer.println(getClass().getName());
            writer.println(score + "," + lives + "," + name + "," + level + "," + complexity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readComponentData(String dataComponent) {
        String[] dataArray = dataComponent.split(",");
        score = Integer.parseInt(dataArray[0]);
        lives = Integer.parseInt(dataArray[1]);
        name = dataArray[2];
        level = Integer.parseInt(dataArray[3]);
        complexity = dataArray[4];
    }

    public void readComponentDataFromJSON (JsonNode rootNode) {
        JsonNode statisticsNode = rootNode.get("statistics");
        score = (statisticsNode.get("score").asInt());
        lives = (statisticsNode.get("lives").asInt());
        name = (statisticsNode.get("name").asText());
        level = (statisticsNode.get("level").asInt());
        complexity = (statisticsNode.get("complexity").asText());
    }


}
