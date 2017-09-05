package excel.Util;

/**
 * @author Alexander Diachenko.
 */
public class RegexUtil {
    /**
     * Method needed for getting numeric regex
     * @return numeric regex
     */
    public static String getNumericRegex() {
        return "|[+]?|[+]?\\d+\\.?|[+]?\\d+\\.?\\d+";
    }
}
