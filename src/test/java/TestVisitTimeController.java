
import analyticsService.controller.VisitTimeController;
import analyticsService.dao.JDBC.AbstractDaoJDBC;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import static org.junit.Assert.assertFalse;

public class TestVisitTimeController {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Date startDate;
    private Date stopDate;

    @Before
    public void setUp() throws ParseException, IOException {
        AbstractDaoJDBC.setConnection("test_connection.properties");
        startDate = format.parse("2017-01-11 00:00:00");
        stopDate = format.parse("2017-01-11 15:45:00");
    }

    @Test
    public void testAverageVisitTime() throws SQLException {
        Map result = VisitTimeController.averageVisitTime("12345");
        assertFalse(result.get("average").equals("00:00:02"));
    }

    @Test
    public void testAverageVisitTimeByTime() {
        Map result = VisitTimeController.averageVisitTimeByTime("12345", new Timestamp(startDate.getTime()),
                new Timestamp(stopDate.getTime()));
        assertFalse(result.get("average").equals("00:00:02"));
    }
}