import java.util.List;

/**
 * Created by User on 13.06.2017.
 */
public class Main {
    public static void main(String[] args) {
        View view = new Console();
        view.write("Введите полный путь к файлу");
        String inputPath = view.read();
        view.write("Введите путь к папке для сохранения");
        String outputPath = view.read();
        ReadWriteExcel readWriteExcel = new ReadWriteExcelImpl();
        List<List<Object>> table = readWriteExcel.read(inputPath);
        Article article = new Article();
        List<List<Object>> fixedTable = article.renameArticle(table);
        readWriteExcel.write(fixedTable, outputPath + "\\updated.xlsx");
    }
}
