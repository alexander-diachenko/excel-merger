package excel.model;


import java.io.File;
import java.util.List;

/**
 * @author Alexander Diachenko
 */
public class ExcelWriteThread implements Runnable {

    private final Excel excel;
    private final List<Integer> articles;
    private final List<Integer> fields;
    private final File fileFrom;
    private final File fileTo;
    private final String path;

    public ExcelWriteThread(final Excel excel, final List<Integer> articles, final List<Integer> fields,
                            final File fileFrom, final File fileTo, final String path) {
        this.excel = excel;
        this.articles = articles;
        this.fields = fields;
        this.fileFrom = fileFrom;
        this.fileTo = fileTo;
        this.path = path;
    }

    @Override
    public void run() {
        try {
            final List<List<Object>> from = excel.read(fileFrom.getAbsolutePath());
            final List<List<Object>> to = excel.read(fileTo.getAbsolutePath());
            final ExcelMerger excelMerger = new ExcelMergerImpl(from, to);
            final List<List<Object>> merged = excelMerger.mergeOneField(articles, fields);
            excel.write(merged, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
