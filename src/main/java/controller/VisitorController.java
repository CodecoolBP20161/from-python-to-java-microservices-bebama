package controller;


import connection.db.AnalyticsDaoJDBC;

import java.sql.SQLException;
import java.sql.Timestamp;

public class VisitorController {

    public static int visitors(Integer webshop) throws SQLException {
         return new AnalyticsDaoJDBC().findByWebshop(webshop).size();
    }

    public static int visitorsByTime(Integer webshop, Timestamp startTime, Timestamp endTime){
        return new AnalyticsDaoJDBC().findByWebshopTime(webshop, startTime, endTime).size();
    }
}
