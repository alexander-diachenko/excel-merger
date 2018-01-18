package excel.components.formatterTab;

import excel.components.formatterTab.components.FilesHBox;
import excel.components.formatterTab.components.FillColumnHBox;
import excel.components.formatterTab.formattThread.ExcelFormatWriteThread;
import excel.model.*;
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
public class FormatterTab extends Tab implements Observer {

    private ExcelFormatWriteThread excelFormatWriteThread;
    private FilesHBox filesHBox;
    private FillColumnHBox fillColumnHBox;
    private Button formatButton;
    private final Text complete = new Text();

    public FormatterTab(final Stage primaryStage) {
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
            setAllDisable(true);
            logicInNewThread(files);
        });

        formatterVBox.getChildren().addAll(filesHBox, fillColumnHBox, formatButton, complete);
        setContent(formatterVBox);
    }

    private void logicInNewThread(final List<File> files) {
        if (files != null) {
            final Excel excel = new ExcelImpl();
            final String columnNumber = fillColumnHBox.getColumnNumber().getText();
            final String columnValue = fillColumnHBox.getColumnValue().getText();
            excelFormatWriteThread = new ExcelFormatWriteThread(excel, files, columnNumber, columnValue);
            excelFormatWriteThread.registerObserver(this);
            new Thread(excelFormatWriteThread).start();
            setComplete(Color.YELLOWGREEN, "Working...");
        }
    }

    @Override
    public void update() {
        excelFormatWriteThread.removeObserver(this);
        setAllDisable(false);
        setComplete(excelFormatWriteThread.getTextColor(), excelFormatWriteThread.getText());
    }

    private void setComplete(final Color color, final String message) {
        complete.setFill(color);
        complete.setText(message);
    }

    private void setAllDisable(final boolean value) {
        filesHBox.setDisable(value);
        fillColumnHBox.setDisable(value);
        formatButton.setDisable(value);
    }
}
