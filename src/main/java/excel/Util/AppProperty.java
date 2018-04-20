package excel.Util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Alexander Diachenko.
 */
public class AppProperty {

    public static Properties getProperty() throws IOException {
        Properties mainProperties = new Properties();
        String path = "./config.properties";
        FileInputStream file = new FileInputStream(path);
        mainProperties.load(file);
        file.close();
        return mainProperties;
    }
}
