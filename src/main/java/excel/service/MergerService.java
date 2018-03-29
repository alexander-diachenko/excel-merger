package excel.service;

import excel.model.Excel;
import excel.model.ExcelMerger;
import excel.model.ExcelMergerImpl;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.File;
import java.util.List;

/**
 * @author Alexander Diachenko.
 */
public class MergerService extends Service<Void> {

    private final Excel excel;
    private final File fileFrom;
    private final File fileTo;
    private final List<Integer> fromColumns;
    private final List<Integer> toColumns;
    private final String savedFilePath;

    public MergerService(Excel excel, File fileFrom, File fileTo, List<Integer> fromColumns, List<Integer> toColumns, String savedFilePath) {
        this.excel = excel;
        this.fileFrom = fileFrom;
        this.fileTo = fileTo;
        this.fromColumns = fromColumns;
        this.toColumns = toColumns;
        this.savedFilePath = savedFilePath;
    }

    @Override
    public Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                List<List<Object>> from = excel.read(fileFrom.getAbsolutePath());
                List<List<Object>> to = excel.read(fileTo.getAbsolutePath());
                ExcelMerger excelMerger = new ExcelMergerImpl();
                List<List<Object>> merged = excelMerger.mergeOneField(from, to, fromColumns, toColumns);
                excel.write(merged, savedFilePath);
                return null;
            }
        };
    }
}
