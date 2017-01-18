
import dao.JDBC.AbstractDaoJDBC;
import dao.JDBC.AnalyticsDaoJDBC;
import model.Analytics;
import model.LocationModel;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static controller.RevenueController.revenueByTime;
import static controller.RevenueController.totalRevenue;
import static org.junit.Assert.assertEquals;

public class TestRevenueController {

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Date startDate;
    private Date stopDate;

    @Before
    public void setUp() throws ParseException, IOException {
        AbstractDaoJDBC.setConnection("connection.properties");
        startDate = format.parse("2017-01-10 00:00:00");
        stopDate = format.parse("2017-01-12 14:45:00");
    }

    @Test
    public void testTotalRevenue() throws SQLException {
        Float result = totalRevenue(1);
        Float expected = (float) 20;
        assertEquals(expected, result);
    }

    @Test
    public void testRevenueByTime() {
        Float result = revenueByTime(1,
                new Timestamp(startDate.getTime()), new Timestamp(stopDate.getTime()));
        Float expected = (float) 20;
        assertEquals(expected, result);
    }
}
