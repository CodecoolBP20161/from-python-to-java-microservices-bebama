import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Created by cickib on 2017.01.09..
 */

public class PropertiesConfig {

    public static void config() {

        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream("./src/main/resources/test_connection.properties");

            // set the properties value
            prop.setProperty("DBURL", "jdbc:postgresql://localhost:5432/testwebshopAnalytics");
            prop.setProperty("DB_USER", "postgres");
            prop.setProperty("DB_PASSWORD", "postgres");

            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
