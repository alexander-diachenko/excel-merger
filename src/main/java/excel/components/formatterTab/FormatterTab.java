package excel.components.formatterTab;

import excel.Util.ExcelUtil;
import excel.components.formatterTab.components.FilesHBox;
import excel.components.formatterTab.components.FillColumnHBox;
import excel.model.Excel;
import excel.model.ExcelImpl;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

/**
 * @author Alexander Diachenko.
 */
public class FormatterTab extends Tab {

    public FormatterTab(Stage primaryStage) {
        setText("Formatter");
        final VBox formatterVBox = new VBox();
        formatterVBox.setPadding(new Insets(10, 50, 50, 50));
        formatterVBox.setSpacing(10);

        final FilesHBox filesHBox = new FilesHBox(primaryStage);

        final FillColumnHBox fillColumnHBox = new FillColumnHBox();

        final Button formatButton = new Button();
        formatButton.setText("Format");
        final Text complete = new Text();
        formatButton.setOnAction(event -> {
            complete.setText("");
            final List<File> files = filesHBox.getFiles();
            final Excel excel = new ExcelImpl();
            if (files != null) {
                for (File file : files) {
                    if (!ExcelUtil.isExcel(file)) {
                        continue;
                    }
                    try {
                        final List<List<Object>> table = excel.read(file.getPath());
                        final int columnNumber = Integer.parseInt(fillColumnHBox.getColumnNumber().getText());
                        final String columnValue = fillColumnHBox.getColumnValue().getText();
                        if(isFilled(columnNumber, columnValue)) {
                            insert(table, columnNumber, columnValue);
                        }
                        excel.write(table, file.getPath());
                        complete.setFill(Color.GREEN);
                        complete.setText("DONE!");
                    } catch (Exception e) {
                        e.printStackTrace();
                        complete.setFill(Color.RED);
                        complete.setText("ERROR!\n" + e.getMessage());
                    }
                }
            }
        });

        formatterVBox.getChildren().addAll(filesHBox, fillColumnHBox, formatButton, complete);
        setContent(formatterVBox);
    }

    private boolean isFilled(final int columnNumber, final String columnValue) {
        return !columnValue.isEmpty() && columnNumber > 0;
    }

    private void insert(final List<List<Object>> table, final int columnNumber, final String fill) {
        final int index = columnNumber - 1;
        for(List<Object> row : table) {
            if(row.size() > index) {
                row.add(index, fill);
            }
        }
    }
}
