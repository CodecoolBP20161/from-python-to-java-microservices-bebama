package analyticsService.model;

import org.apache.http.client.utils.URIBuilder;

import java.net.URISyntaxException;

/**
 * Created by makaimark on 2017.01.11..
 */
public class Graph {
    public static final String API_URL = "http://chart.apis.google.com/chart?";

    public String buildGraphURL(String size, String name, String values, String partition, String colors) throws URISyntaxException {
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
}
