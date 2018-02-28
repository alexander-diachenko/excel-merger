package excel.components.formatterTab;

import excel.components.formatterTab.components.FilesHBox;
import excel.components.formatterTab.components.FillColumnHBox;
import excel.components.formatterTab.formattThread.ExcelFormatWriteThread;
import excel.model.*;
import javafx.beans.binding.BooleanBinding;
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
    private Text complete = new Text();

    public FormatterTab(final Stage primaryStage) {
        setText("Formatter");
        final VBox formatterVBox = new VBox();
        setFormatterVBoxOptions(formatterVBox);

        createElements(primaryStage);
        formatButton.disableProperty().bind(getBooleanBinding());
        formatButton.setOnAction(event -> formatButtonActions());

        formatterVBox.getChildren().addAll(filesHBox, fillColumnHBox, formatButton, complete);
        setContent(formatterVBox);
    }

    private void setFormatterVBoxOptions(VBox vBox) {
        vBox.setPadding(new Insets(10, 50, 50, 50));
        vBox.setSpacing(10);
    }

    private void createElements(Stage primaryStage) {
        filesHBox = new FilesHBox(primaryStage);
        fillColumnHBox = new FillColumnHBox();
        formatButton = new Button("Format");
    }

    private void formatButtonActions() {
        final List<File> files = filesHBox.getFiles();
        setAllDisable(true);
        logicInNewThread(files);
    }

    private void setAllDisable(final boolean value) {
        filesHBox.setDisable(value);
        fillColumnHBox.setDisable(value);
        formatButton.disableProperty().bind(new BooleanBinding() {
            @Override
            protected boolean computeValue() {
                return true;
            }
        });
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
        formatButton.disableProperty().bind(getBooleanBinding());
        setComplete(excelFormatWriteThread.getTextColor(), excelFormatWriteThread.getText());
    }

    private void setComplete(final Color color, final String message) {
        complete.setFill(color);
        complete.setText(message);
    }

    private BooleanBinding getBooleanBinding() {
        return filesHBox.getSelectedFilesCount().textProperty().isEmpty();
    }
}
