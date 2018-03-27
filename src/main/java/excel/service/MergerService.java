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

    private Excel excel;
    private File fileFrom;
    private File fileTo;
    private List<Integer> fromColumns;
    private List<Integer> toColumns;
    private String savedFilePath;

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
