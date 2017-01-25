import analyticsService.controller.AbstractController;
import analyticsService.model.Analytics;
import analyticsService.model.LocationModel;
import analyticsService.model.LocationVisitor;
import analyticsService.model.Webshop;
import analyticsService.service.APIService;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestAPIService {

    APIService service = new APIService();
    SimpleDateFormat dt = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

    @Test
    public void testSetParams() throws ParseException {
        Date startDate = dt.parse("2017-01-20 08:10:00");
        Date stopDate = dt.parse("2017-01-20 08:15:00");
        service.setParams("webshop", startDate, stopDate);
        assertTrue(service.getParams().size() == 3);
    }

    @Test
    public void testSetParamsNullDate() throws ParseException {
        Date startDate = null;
        Date stopDate = null;
        service.setParams("webshop2", startDate, stopDate);
        assertEquals(service.getParams().size(), 1);
    }

    @Test
    public void testGetParams() throws ParseException {
        Date startDate = dt.parse("2017-01-20 08:10:00");
        Date stopDate = dt.parse("2017-01-20 08:15:00");
        service.setParams("webshop", startDate, stopDate);
        assertTrue(service.getParams().containsKey("webshop"));
    }

    @Test
    public void testVisitorCount() throws ParseException {
        LocationModel location = new LocationModel("Budapest", "Hungary", "HU");
        Date startDate = dt.parse("2017-01-20 08:10:00");
        Date stopDate = dt.parse("2017-01-20 08:15:00");
        Webshop shop = new Webshop("webshop", "key");
        Analytics model = new Analytics(shop, "12345", new Timestamp(startDate.getTime()), new Timestamp(stopDate.getTime()), location);
        List<Analytics> list = new ArrayList<>();
        list.add(model);
        service.visitorCount(list);
        assertTrue(service.getParams().containsKey("visitors"));
    }

    @Test
    public void testLocationVisits() throws ParseException {
        LocationModel location = new LocationModel("Budapest", "Hungary", "HU");
        LocationModel location2 = new LocationModel("Miskolc", "Hungary", "HU");

        LocationVisitor visitormodel1 = new LocationVisitor(location, 10);
        LocationVisitor visitormodel2 = new LocationVisitor(location2, 10);

        List<LocationVisitor> list = new ArrayList<>();
        list.add(visitormodel1);
        list.add(visitormodel2);
        service.locationVisits(list);
        assertEquals(service.getParams().get("locations").toString(), "{Budapest, Hungary, HU=10, Miskolc, Hungary, HU=10}");
    }

    @Test
    public void testMostVisitorsFrom() {
        LocationModel location = new LocationModel("Budapest", "Hungary", "HU");
        LocationModel location2 = new LocationModel("Miskolc", "Hungary", "HU");

        LocationVisitor visitormodel1 = new LocationVisitor(location, 5);
        LocationVisitor visitormodel2 = new LocationVisitor(location2, 10);

        List<LocationVisitor> list = new ArrayList<>();
        list.add(visitormodel1);
        list.add(visitormodel2);

        service.mostVisitsFrom(list);
        assertEquals(service.getParams().get("most_visited_from"), "Miskolc, Hungary, HU");
    }

    @Test
    public void testVisitTimeCount() throws ParseException {
        LocationModel location = new LocationModel("Budapest", "Hungary", "HU");
        Date startDate = dt.parse("2017-01-20 08:10:00");
        Date stopDate = dt.parse("2017-01-20 08:15:00");
        Webshop shop = new Webshop("webshop", "key");
        Analytics model = new Analytics(shop, "12345", new Timestamp(startDate.getTime()), new Timestamp(stopDate.getTime()), location);
        List<Analytics> list = new ArrayList<>();
        list.add(model);
        service.visitTimeCount(list);
        assertEquals(service.getParams().get("times").toString(), "{average_time_spent=00:05:00, min_time_spent=00:05:00, max_time_spent=00:05:00}");
    }
}
