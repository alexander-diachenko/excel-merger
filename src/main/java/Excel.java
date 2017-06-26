import java.util.List;

/**
 * Created by User on 13.06.2017.
 */
public interface Excel {

    List<List<Object>> read(final String path) throws ExcelException;

    void write(final List<List<Object>> table, final String path) throws ExcelException;

    int getWorkbookSize(final String path) throws ExcelException;
}
