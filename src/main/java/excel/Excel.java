package excel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.List;

/**
 * @author Alexander Diachenko.
 */
public interface Excel {
    /**
     * Return list representation of excel file.
     * @param path Path to excel file.
     * @return List representation of excel file.
     * @throws IOException Throws IOException if file read failed.
     * @throws InvalidFormatException Throws InvalidFormatException if it is not excel file(.xls or .xlsx).
     */
    List<List<Object>> read(final String path) throws IOException, InvalidFormatException;

    /**
     * Write List<List<>> to excel file.
     * @param table Data in List<List<>>.
     * @param path Path to new excel file.
     * @throws IOException Throws IOException if file write failed.
     */
    void write(final List<List<Object>> table, final String path) throws IOException;
}
