package excel.service;

import excel.Util.ExcelUtil;
import excel.Util.TimeUtil;
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

    private List<File> files;
    private Excel excel;
    private String saveDirectoryPath;

    public AllInService(List<File> files, Excel excel, String saveDirectoryPath) {
        this.files = files;
        this.excel = excel;
        this.saveDirectoryPath = saveDirectoryPath;
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
                String saveFilePath = saveDirectoryPath + "\\" + "AllIn_" + TimeUtil.getCurrentTime() + ".xlsx";
                excel.write(tables, saveFilePath);
                return null;
            }
        };
    }
}
