import java.io.IOException;
import java.util.List;

/**
 * Created by User on 13.06.2017.
 */
public interface Excel {

    List<List<Object>> read(final String path) throws IOException;

    void write(final List<List<Object>> table, final String path) throws IOException;

    int getWorkbookSize(final String path) throws IOException;
}
