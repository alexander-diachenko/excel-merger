package excel.components.formatterTab;

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
import org.apache.commons.io.FilenameUtils;

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
                    if (!isExcel(file)) {
                        continue;
                    }
                    try {
                        final List<List<Object>> table = excel.read(file.getPath());
                        final String columnNumber = fillColumnHBox.getColumnNumber().getText();
                        final String fill = fillColumnHBox.getFill().getText();
                        if(!columnNumber.isEmpty() && !fill.isEmpty()) {
                            for(List<Object> row : table) {
                                final int index = Integer.parseInt(columnNumber) - 1;
                                if(row.size() > index) {
                                    row.add(index, fill);
                                }
                            }
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

    private boolean isExcel(final File file) {
        return FilenameUtils.isExtension(file.getName(), "xls") || FilenameUtils.isExtension(file.getName(), "xlsx");
    }
}
