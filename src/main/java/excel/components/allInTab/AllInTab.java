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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Alexander Diachenko.
 */
public class AllInTab extends Tab implements Observer {

    private FilesHBox filesHBox;
    private FileDirectoryHBox fileDirectoryHBox;
    private HBox allInOpenHBox;
    private Button allInButton;
    private Button openButton;
    private ExcelAllInThread excelAllInThread;
    private Text complete = new Text();

    public AllInTab(final Stage primaryStage) {
        setText("All In");
        final VBox allInVBox = new VBox();
        setAllInVBoxOptions(allInVBox);

        createElements(primaryStage);

        allInButton.disableProperty().bind(getBooleanBinding());
        allInButton.setOnAction(event -> allInButtonActions());
        openButton.setOnAction(event -> openButtonActions());
        allInVBox.getChildren().addAll(filesHBox, fileDirectoryHBox, allInOpenHBox, complete);
        setContent(allInVBox);
    }

    private void setAllInVBoxOptions(VBox vBox) {
        vBox.setPadding(new Insets(10, 50, 50, 50));
        vBox.setSpacing(10);
    }

    private void createElements(Stage primaryStage) {
        filesHBox = new FilesHBox(primaryStage);
        fileDirectoryHBox = new FileDirectoryHBox(primaryStage);
        allInOpenHBox = createAllInOpenHBox();
    }

    private HBox createAllInOpenHBox() {
        HBox allInOpenHBox = new HBox(10);
        allInButton = new Button("All in");
        openButton = new Button("Open");
        openButton.setDisable(true);
        allInOpenHBox.getChildren().addAll(allInButton, openButton);
        return allInOpenHBox;
    }

    private void allInButtonActions() {
        final String directoryPath = fileDirectoryHBox.getFileDirectory().getAbsolutePath();
        final List<File> files = filesHBox.getFiles();
        final Excel excel = new ExcelImpl();
        setAllDisable(true);
        logicInNewThread(excel, files, directoryPath);
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
        if(excelAllInThread.getTextColor().equals(Color.GREEN)) {
            openButton.setDisable(false);
        }
    }

    private void openButtonActions() {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(new File(excelAllInThread.getAbsolutePath()));
            openButton.setDisable(true);
        } catch (IOException e) {
            setComplete(Color.RED, e.getMessage());
            e.printStackTrace();
        }
    }

    private void setComplete(final Color color, final String message) {
        complete.setFill(color);
        complete.setText(message);
    }

    private BooleanBinding getBooleanBinding() {
        return fileDirectoryHBox.getFileDirectoryPath().textProperty().isEmpty().or(
                filesHBox.getSelectedFilesCount().textProperty().isEmpty());
    }
}
