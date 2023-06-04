import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.*;
import java.util.List;

public class Proxy {
    public void serializeToTextFile(String filename, List<DisplayObject> displayObjects, Settings settings, Player player) {
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write("");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (DisplayObject displayObject: displayObjects)
        {
            displayObject.saveComponentData(filename);
        }
        settings.saveComponentData(filename);
        player.statistics.saveComponentData(filename);
    }

    public void deserializeFromTextFile(String filename, DisplayAll allObjects, Settings settings, Player player) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            int length = allObjects.displayObjects.size();
            for (int i = 0; i < length; i++) {
                reader.readLine();
                allObjects.displayObjects.get(i).readComponentData(reader.readLine());
            }
            reader.readLine();
            settings.readComponentData(reader.readLine());
            settings.update();
            reader.readLine();
            player.statistics.readComponentData(reader.readLine());
            TableRecords.update();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serializeToJSONFile(String filename, List<DisplayObject> displayObjects, Settings settings, Player player) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        //mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        ObjectNode settingsNode = mapper.valueToTree(settings);
        root.set("settings", settingsNode);

        ObjectNode statisticsNode = mapper.valueToTree(player.statistics);
        root.set("statistics", statisticsNode);

        ArrayNode displayObjectsNode = root.putArray("displayObjects");
        for (DisplayObject displayObject: displayObjects) {
            ObjectNode objectNode = mapper.valueToTree(displayObject);
            displayObjectsNode.add(objectNode);
        }

        try {
            File file = new File(filename);
            mapper.writeValue(file, root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deserializeFromJSONFile(String filename, DisplayAll allObjects, Settings settings, Player player) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode rootNode = mapper.readTree(new File(filename));
            allObjects.readDataComponentFromJSON(rootNode);
            settings.readComponentDataFromJSON(rootNode);
            settings.update();
            player.statistics.readComponentDataFromJSON(rootNode);
            TableRecords.update();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
