package excel.Util;

import org.apache.commons.io.FilenameUtils;

import java.io.File;

/**
 * @author Alexander Diachenko.
 */
public class ExcelUtil {

    /**
     * Method needed to check if file is a excel file.
     *
     * @param file that need to be check
     * @return true if excel
     */
    public static boolean isExcel(final File file) {
        return FilenameUtils.isExtension(file.getName(), "xls") || FilenameUtils.isExtension(file.getName(), "xlsx");
    }
}
