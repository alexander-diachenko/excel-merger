package excel.Util;

import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Alexander Diachenko.
 */
public class FileUtil {

    public static void open(File file) throws IOException {
        Desktop desktop = Desktop.getDesktop();
        desktop.open(file);
    }
}
