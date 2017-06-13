import java.util.List;

/**
 * Created by User on 13.06.2017.
 */
public interface ReadWriteExcel {

    List<List<Object>> read(String path);

    void write(List<List<Object>> table, String path);
}
