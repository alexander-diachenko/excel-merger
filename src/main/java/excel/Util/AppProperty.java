package excel.Util;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

/**
 * @author Alexander Diachenko.
 */
public class AppProperty {

    private final static Logger logger = Logger.getLogger(AppProperty.class);

    public static Properties getProperty() {
        Properties mainProperties = new Properties();
        String path = "./config.properties";
        try {
            FileInputStream file = new FileInputStream(path);
            mainProperties.load(file);
            file.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return mainProperties;
    }

    public static Properties setProperties(Properties properties) {
        OutputStream output = null;
        try {
            output = new FileOutputStream("./config.properties");
            properties.store(output, null);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return properties;
    }
}
