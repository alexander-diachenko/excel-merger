package excel.components.allInTab;

import excel.components.allInTab.allInThread.ExcelAllInThread;
import excel.components.formatterTab.components.FilesHBox;
import excel.components.mergerTab.components.FileDirectoryHBox;
import excel.model.Excel;
import excel.model.ExcelImpl;
import excel.model.Observer;
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
public class AllInTab extends Tab implements Observer {

    private FilesHBox filesHBox;
    private FileDirectoryHBox fileDirectoryHBox;
    private Button allInButton;
    private ExcelAllInThread excelAllInThread;
    private final Text complete = new Text();

    public AllInTab(final Stage primaryStage) {
        setText("All In");
        final VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 50, 50, 50));
        vBox.setSpacing(10);

        filesHBox = new FilesHBox(primaryStage);
        fileDirectoryHBox = new FileDirectoryHBox(primaryStage);
        allInButton = new Button();
        allInButton.setText("All in");
        allInButton.disableProperty().bind(getBooleanBinding());
        allInButton.setOnAction(event -> {
            final String directoryPath = fileDirectoryHBox.getFileDirectory().getAbsolutePath();
            final List<File> files = filesHBox.getFiles();
            final Excel excel = new ExcelImpl();
            if (files != null) {
                setAllDisable(true);
                logicInNewThread(excel, files, directoryPath);
            }
        });
        vBox.getChildren().addAll(filesHBox, fileDirectoryHBox, allInButton, complete);
        setContent(vBox);
    }

    private void logicInNewThread(final Excel excel, final List<File> files, final String directoryPath) {
        excelAllInThread = new ExcelAllInThread(excel, files, directoryPath);
        excelAllInThread.registerObserver(this);
        new Thread(excelAllInThread).start();
        setComplete(Color.YELLOWGREEN, "Working...");
    }

    @Override
    public void update() {
        excelAllInThread.removeObserver(this);
        setAllDisable(false);
        allInButton.disableProperty().bind(getBooleanBinding());
        setComplete(excelAllInThread.getTextColor(), excelAllInThread.getText());
    }

    private void setComplete(final Color color, final String message) {
        complete.setFill(color);
        complete.setText(message);
    }

    private void setAllDisable(final boolean value) {
        filesHBox.setDisable(value);
        fileDirectoryHBox.setDisable(value);
        allInButton.disableProperty().bind(new BooleanBinding() {
            @Override
            protected boolean computeValue() {
                return true;
            }
        });
    }

    private BooleanBinding getBooleanBinding() {
        return fileDirectoryHBox.getFileDirectoryPath().textProperty().isEmpty().or(
                filesHBox.getSelectedFilesCount().textProperty().isEmpty());
    }
}
