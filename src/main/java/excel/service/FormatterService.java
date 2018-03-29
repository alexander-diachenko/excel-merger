package excel.service;

import excel.Util.ExcelUtil;
import excel.model.Excel;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.CheckBox;

import java.io.File;
import java.util.List;

/**
 * @author Alexander Diachenko.
 */
public class FormatterService extends Service<Void> {

    private final Excel excel;
    private final List<File> files;
    private final CheckBox options;
    private final Integer field;
    private final String value;

    public FormatterService(Excel excel, List<File> files, CheckBox options, Integer field, String value) {
        this.excel = excel;
        this.files = files;
        this.options = options;
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
                    if (options.isSelected() && !value.isEmpty()) {
                        insert(table, field, value);
                    }
                    excel.write(table, file.getPath());
                }
                return null;
            }
        };
    }

    private void insert(List<List<Object>> table, int columnNumber, final String columnValue) {
        int index = columnNumber - 1;
        for (List<Object> row : table) {
            if (row.size() > index) {
                row.add(index, columnValue);
            }
        }
    }
}
