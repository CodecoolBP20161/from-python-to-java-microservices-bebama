package analyticsService.controller;

import analyticsService.model.Graph;
import spark.Request;
import spark.Response;

import java.io.IOException;

public class GraphController {

    public static String createGraph(Request req, Response res) throws IOException {
       Graph.formTheData(req.queryParams("result"));
        return "";
    }
}
