import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by User on 13.06.2017.
 */
public class Main {
    public static void main(String[] args) {
        ReadWriteExcel readWriteExcel = new ReadWriteExcelImpl();
        List<List<Object>> table = readWriteExcel.read("D:\\Downloads\\test1.xlsx");
        Article article = new Article();
        List<List<Object>> fixedTable = article.renameArticle(table);
        List<Object> row1 = Arrays.asList("1", "test1", "lol1");
        List<Object> row2 = Arrays.asList("2", "test2", "lol2");
        List<Object> row3 = Arrays.asList("3", "test3", "lol3");
        List<List<Object>> test = new ArrayList<>();
        test.add(row1);
        test.add(row2);
        test.add(row3);
        readWriteExcel.write(test, "D:\\Downloads\\updated.xlsx");
    }
}
