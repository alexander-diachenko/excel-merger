import java.io.IOException;
import java.util.List;

/**
 * Created by User on 13.06.2017.
 */
public interface Excel {

    List<List<Object>> read(String path) throws IOException;

    void write(List<List<Object>> table, String path) throws IOException;
}
