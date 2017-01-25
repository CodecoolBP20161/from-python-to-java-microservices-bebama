package model;

import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class Graph {
    private static final String API_URL = "http://chart.apis.google.com/chart?";

    private static List<String > colors = Arrays.asList("000099", "669900", "7D858F","586F8E", "cc6699", "6600ff", "339966", "660066");

    private String buildGraphURL(String size, String name, String values, String partition, String colors) throws URISyntaxException {
        URIBuilder builder = new URIBuilder(API_URL);
        builder.addParameter("chs", size); // 200x400
        builder.addParameter("chdlp", "b"); // must have :D
        builder.addParameter("chtt", name);
        builder.addParameter("chdl", values); // needed format: Asleep|Awake|randomthing
        builder.addParameter("chd", "t:" + partition); // needed format: 1,89,10 ( 100 max)
        builder.addParameter("cht", "p"); // must have :D
        builder.addParameter("chco", colors); // needed format: 7D858F,586F8E,7D858F

        return String.valueOf(builder.build());
    }

    private static Map<String, Object> jsonToMap(String jsonString) throws IOException {
        JSONObject object = new JSONObject(jsonString);
        Map<String, Object> map = new HashMap<>();
        Iterator<?> keyset = object.keys();

        while (keyset.hasNext()) {
            String key = (String) keyset.next();
            Object value = object.get(key);
            map.put(key, value);
        }
        return map;
    }

    private static String collectLocations(Map<String, Object> locations) {
        String result = "";
        for (Object o : locations.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            result += String.valueOf(pair.getKey()) + " " + String.valueOf(pair.getValue()) + "|";
        }
        return result.substring(0, result.length()-1);
    }

    private static String countRates(Map<String, Object> locations) {
        String result = "";
        for (Object o : locations.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            result += String.valueOf(pair.getValue()) + ",";
        }
        return result.substring(0, result.length()-1);
    }

    private static String getColors(Integer size) {
        String result = "";
        String color;
        Random randomizer = new Random();
        List<String> choosedColors = colors;
        for ( int i = 0; i < size; i++) {
            color = colors.get(randomizer.nextInt(colors.size())) + ",";
            choosedColors.remove(color);
            result += color;
        }
        return result.substring(0, result.length()-1);
    }

    public static String formTheData(String jsonString) throws IOException {
        Graph graph = new Graph();
        Map<String, Object> map = jsonToMap(jsonString);
        JSONObject object = (JSONObject) map.get("locations");
        Map<String, Object> locations = jsonToMap(object.toString());
        try {
            String url = graph.buildGraphURL("300x400", "Visitors location", collectLocations(locations),
                    countRates(locations), getColors(locations.size()));
            return url;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return "";
    }
}
