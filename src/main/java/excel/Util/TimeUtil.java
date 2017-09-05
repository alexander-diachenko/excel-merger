package excel.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Alexander Diachenko.
 */
public class TimeUtil {

    /**
     * Method needed for getting current time in format dd-MM-yyyy_HH-mm-ss
     * @return current time dd-MM-yyyy_HH-mm-ss
     */
    public static String getCurrentTime() {
        return new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
    }
}
