import java.util.List;

/**
 * Created by User on 13.06.2017.
 */
public class Main {

    public static void main(String[] args) {
        ReadWriteExcel readWriteExcel = new ReadWriteExcel();
        List<List<Object>> table = readWriteExcel.read();
        readWriteExcel.write(table);
    }
}
