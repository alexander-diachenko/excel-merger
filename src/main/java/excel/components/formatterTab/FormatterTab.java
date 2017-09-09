package excel.components.formatterTab;

import com.sun.jmx.snmp.Timestamp;
import excel.Util.ExcelUtil;
import excel.Util.ThreadListener;
import excel.components.formatterTab.components.FilesHBox;
import excel.components.formatterTab.components.FillColumnHBox;
import excel.model.Excel;
import excel.model.ExcelFormatWriteThread;
import excel.model.ExcelImpl;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @author Alexander Diachenko.
 */
public class FormatterTab extends Tab implements ThreadListener {

    private ExcelFormatWriteThread excelFormatWriteThread;
    private FilesHBox filesHBox;
    private FillColumnHBox fillColumnHBox;
    private Button formatButton;
    private final Text complete = new Text();

    public FormatterTab(Stage primaryStage) {
        setText("Formatter");
        final VBox formatterVBox = new VBox();
        formatterVBox.setPadding(new Insets(10, 50, 50, 50));
        formatterVBox.setSpacing(10);

        filesHBox = new FilesHBox(primaryStage);
        fillColumnHBox = new FillColumnHBox();
        formatButton = new Button();
        formatButton.setText("Format");
        formatButton.setOnAction(event -> {
            final List<File> files = filesHBox.getFiles();
            if (files != null) {
                setAllDisable(true);
                final Excel excel = new ExcelImpl();
                for (File file : files) {
                    if (!ExcelUtil.isExcel(file)) {
                        continue;
                    }
                    final String columnNumber = fillColumnHBox.getColumnNumber().getText();
                    final String columnValue = fillColumnHBox.getColumnValue().getText();
                    logic(excel, file, columnNumber, columnValue);
                }
            }
        });

        formatterVBox.getChildren().addAll(filesHBox, fillColumnHBox, formatButton, complete);
        setContent(formatterVBox);
    }

    private void logic(Excel excel, File file, String columnNumber, String columnValue) {
        excelFormatWriteThread = new ExcelFormatWriteThread(excel, file, columnNumber, columnValue);
        excelFormatWriteThread.addListener(this);
        new Thread(excelFormatWriteThread).start();
        setComplete(Color.YELLOWGREEN, "Working...");
    }

    @Override
    public void notifyOfThread(Thread thread) {
        setAllDisable(false);
        setComplete(excelFormatWriteThread.getTextColor(), excelFormatWriteThread.getText());
    }

    private void setComplete(Color color, String message) {
        complete.setFill(color);
        complete.setText(message);
    }

    private void setAllDisable(boolean value) {
        filesHBox.setDisable(value);
        fillColumnHBox.setDisable(value);
        formatButton.setDisable(value);
    }
}
