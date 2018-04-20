package excel.Util;

import java.io.*;
import java.util.Properties;

/**
 * @author Alexander Diachenko.
 */
public class AppProperty {

    public static Properties getProperty() {
        Properties mainProperties = new Properties();
        String path = "./config.properties";
        try {
            FileInputStream file = new FileInputStream(path);
            mainProperties.load(file);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mainProperties;
    }

    public static Properties setProperties(Properties properties) {
        OutputStream output = null;
        try {
            output = new FileOutputStream("./config.properties");
            properties.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }
}
