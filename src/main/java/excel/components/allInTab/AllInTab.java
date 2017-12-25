package excel.components.allInTab;

import excel.Util.ExcelUtil;
import excel.Util.TimeUtil;
import excel.components.formatterTab.components.FilesHBox;
import excel.components.mergerTab.components.FileDirectoryHBox;
import excel.model.Excel;
import excel.model.ExcelImpl;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Diachenko.
 */
public class AllInTab extends Tab {

    private FilesHBox filesHBox;
    private FileDirectoryHBox fileDirectoryHBox;
    private Button allInButton;

    public AllInTab(Stage primaryStage) {
        setText("All In");
        final VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 50, 50, 50));
        vBox.setSpacing(10);

        filesHBox = new FilesHBox(primaryStage);
        fileDirectoryHBox = new FileDirectoryHBox(primaryStage);
        allInButton = new Button();
        allInButton.setText("All in");
        allInButton.setOnAction(event -> {
            final List<List<Object>> tables = new ArrayList<>();
            final List<File> files = filesHBox.getFiles();
            final Excel excel = new ExcelImpl();
            if (files != null) {
                for (File file : files) {
                    if (!ExcelUtil.isExcel(file)) {
                        continue;
                    }
                    try {
                        tables.addAll(excel.read(file.getAbsolutePath()));
                    } catch (IOException | InvalidFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                final String absolutePath = fileDirectoryHBox.getFileDirectory().getAbsolutePath() + "\\" + "AllIn_" + TimeUtil.getCurrentTime() + ".xlsx";
                excel.write(tables, absolutePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        vBox.getChildren().addAll(filesHBox, fileDirectoryHBox, allInButton);
        setContent(vBox);
    }
}
