package excel.service;

import excel.Util.ExcelUtil;
import excel.model.Excel;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.File;
import java.util.List;

/**
 * @author Alexander Diachenko.
 */
public class FormatterService extends Service<Void> {

    private Excel excel;
    private List<File> files;
    private String field;
    private String value;

    public FormatterService(Excel excel, List<File> files, String field, String value) {
        this.excel = excel;
        this.files = files;
        this.field = field;
        this.value = value;
    }

    @Override
    public Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (File file : files) {
                    if (!ExcelUtil.isExcel(file)) {
                        continue;
                    }
                    final List<List<Object>> table = excel.read(file.getPath());
                    if (isCorrect(field, value)) {
                        insert(table, Integer.parseInt(field), value);
                    }
                    excel.write(table, file.getPath());
                }
                return null;
            }
        };
    }

    private boolean isCorrect(final String columnNumber, final String columnValue) {
        return !columnValue.isEmpty() && !columnNumber.isEmpty() && Integer.parseInt(columnNumber) > 0;
    }

    private void insert(final List<List<Object>> table, final int columnNumber, final String columnValue) {
        final int index = columnNumber - 1;
        for (List<Object> row : table) {
            if (row.size() > index) {
                row.add(index, columnValue);
            }
        }
    }
}
