package excel.service;

import excel.Util.ExcelUtil;
import excel.model.Excel;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Alexander Diachenko.
 */
public class AllInService extends Service<Void> {

    private final List<File> files;
    private final Excel excel;
    private final String savedFilePath;

    public AllInService(List<File> files, Excel excel, String savedFilePath) {
        this.files = files;
        this.excel = excel;
        this.savedFilePath = savedFilePath;
    }

    @Override
    public Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                final List<List<Object>> tables = new LinkedList<>();
                for (File file : files) {
                    if (!ExcelUtil.isExcel(file)) {
                        continue;
                    }
                    tables.addAll(excel.read(file.getAbsolutePath()));
                }
                excel.write(tables, savedFilePath);
                return null;
            }
        };
    }
}
