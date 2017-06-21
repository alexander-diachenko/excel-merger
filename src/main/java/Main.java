import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by User on 13.06.2017.
 */
public class Main {
    public static void main(String[] args) {
        List<Integer> articles = Arrays.asList(0, 0);
        List<Integer> fields = Arrays.asList(1, 1);

        ReadWriteExcel readWriteExcel = new ReadWriteExcelImpl();
        List<List<Object>> tableTo = readWriteExcel.read("D:\\Downloads\\to.xlsx");
        List<List<Object>> tableFrom = readWriteExcel.read("D:\\Downloads\\from.xlsx");

        MergeExcel mergeExcel = new MergeExcelImpl(tableFrom, tableTo);
        List<List<Object>> mergedTable = mergeExcel.mergeOneField(articles, fields);
        readWriteExcel.write(mergedTable, "D:\\Downloads\\merged.xlsx");
        new Console().write("Готово!");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
