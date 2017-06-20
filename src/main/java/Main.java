import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 13.06.2017.
 */
public class Main {
    public static void main(String[] args) {
        List<Integer> articles = new ArrayList<>();
        List<Integer> fields = new ArrayList<>();
        View view = new Console();
        view.write("Введите полный путь к конечному файлу");
        String inputPathTo = view.read();
        view.write("Введите полный путь к начальному файлу");
        String inputPathFrom = view.read();
        view.write("Введите номер колонки актикла конечного файла");
        String inputArticleTo = view.read();
        articles.add(Integer.valueOf(inputArticleTo));
        view.write("Введите номер колонки артикла начального файла");
        String inputArticleFrom = view.read();
        articles.add(Integer.valueOf(inputArticleFrom));
        view.write("Введите номер колонки которую нужно скопировать конечного файла");
        String inputColumnTo = view.read();
        fields.add(Integer.valueOf(inputColumnTo));
        view.write("Введите номер колонки которую нужно скопировать начального файла");
        String inputColumnFrom = view.read();
        fields.add(Integer.valueOf(inputColumnFrom));
        view.write("Введите путь к папке для сохранения");
        String outputPath = view.read();

        ReadWriteExcel readWriteExcel = new ReadWriteExcelImpl();
        List<List<Object>> tableTo = readWriteExcel.read(inputPathTo);
        List<List<Object>> tableFrom = readWriteExcel.read(inputPathFrom);

        MergeExcel mergeExcel = new MergeExcelImpl(tableFrom, tableTo);
        List<List<Object>> mergedTable = mergeExcel.mergeOneField(articles, fields);
        readWriteExcel.write(mergedTable, outputPath + "\\updated.xlsx");
        view.write("Готово!");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
