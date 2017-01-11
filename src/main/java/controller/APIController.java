package controller;

import connection.db.AnalyticsDaoJDBC;
import model.Analytics;
import model.LocationModel;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class APIController {

    private String sessionId;
    private int webShopId = 1;
    private Timestamp startTime;
    private Timestamp endTime;
    private Date start;
    private Date stop;
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Map<String, Integer> topLocations;

    private Date customDateParser(String inputDate) throws ParseException {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            Date tempDate = inputFormat.parse(inputDate);
            String formattedDate = format.format(tempDate);
            return format.parse(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ModelAndView renderMain(Request req, Response res) {
        Map<Object, Object> params = new HashMap<>();
        startSession(req, res);
        return new ModelAndView(params, "time_location");
    }

    private void getTimes(Request req, Response res) throws ParseException {
        start = customDateParser(req.queryParams("startTime"));
        stop = customDateParser(req.queryParams("endTime"));
    }

    public String api(Request req, Response res) throws ParseException, SQLException {
        topLocations = LocationVisitorController.topLocations(Integer.parseInt(req.queryParams("webshopId")));
        int highestVisitorCount = Collections.max(topLocations.values());
        String topLocation = topLocations.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), highestVisitorCount))
                .map(Map.Entry::getKey).findFirst().orElse(null);
        return String.format("{'totalVisitorCount': %s, 'mostVisitorsFrom': %s, 'averageVisitTime': %s, 'totalRevenue': %s}",
                visitorCounter(req, res), topLocation, visitTimeCounter(req, res), countRevenue(req, res));
    }

    public String visitTimeCounter(Request req, Response res) throws ParseException, SQLException {
        webShopId = Integer.parseInt(req.queryParams("webshopId"));
        sessionId = req.queryParams("sessionId");
        if (req.queryParams().size() == 3) {
            getTimes(req, res);
            return VisitTimeController.averageVisitTimeByTime(webShopId, convertToTimeStamp(start), convertToTimeStamp(stop));
        } else {
            return VisitTimeController.averageVisitTime(webShopId);
        }
    }

    public int visitorCounter(Request req, Response res) throws ParseException, SQLException {
        webShopId = Integer.parseInt(req.queryParams("webshopId"));
        sessionId = req.queryParams("sessionId");

        if (req.queryParams().size() == 3) {
            getTimes(req, res);
            return VisitorController.visitorsByTime(webShopId, convertToTimeStamp(start), convertToTimeStamp(stop));
        } else {
            return VisitorController.visitors(webShopId);
        }
    }

    public String stopSession(Request req, Response res) {
        String time = req.queryParams("time");
        Date date = new Date(Long.parseLong(time));
        this.endTime = convertToTimeStamp(date);
        analytics(req, res);
        return "";
    }

    public String startSession(Request req, Response res) {
        sessionId = req.session().id();
        Date date = new Date();
        this.startTime = convertToTimeStamp(date);
        return "";
    }

    public Float countRevenue(Request req, Response res) throws ParseException, SQLException {
        sessionId = req.queryParams("sessionId");
        webShopId = Integer.parseInt(req.queryParams("webshopId"));

        if (req.queryParams().size() == 3) {
            getTimes(req, res);
            return RevenueController.revenueByTime(webShopId, convertToTimeStamp(start), convertToTimeStamp(stop));
        } else {
            return RevenueController.totalRevenue(webShopId);
        }
    }

    public int getWebShopId() {
        return webShopId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void analytics(Request req, Response res) {
        LocationModel location = LocationModel.getAllLocations().get(0);
        float amount = 10;
        Currency currency = Currency.getInstance(Locale.US);
        Analytics model = new Analytics(getWebShopId(), getSessionId(), this.startTime, this.endTime, location, amount, String.valueOf(currency));
        try {
            AnalyticsDaoJDBC.add(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Timestamp convertToTimeStamp(Date date) {
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.MILLISECOND, 0);
            return new java.sql.Timestamp(date.getTime());
        } else {
            return null;
        }
    }

    public Map locationVisits(Request req, Response res) throws SQLException, ParseException {
        sessionId = req.queryParams("sessionId");
        webShopId = Integer.parseInt(req.queryParams("webshopId"));
        if (req.queryParams().size() == 3) {
            getTimes(req, res);
            return LocationVisitorController.topLocationsByTime(webShopId, convertToTimeStamp(start), convertToTimeStamp(stop));
        } else {
            return topLocations = LocationVisitorController.topLocations(webShopId);
        }
    }

}