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
        readWriteExcel.write(fixedTable, "D:\\Downloads\\updated.xlsx");
    }
}
