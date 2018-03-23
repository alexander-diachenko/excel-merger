package excel.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

/**
 * @author Alexander Diachenko.
 */
public class AllInController {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button files;
    @FXML
    private Button saveDirectory;
    @FXML
    private Button allIn;
    @FXML
    private Button open;
    @FXML
    private Label filesCount;
    @FXML
    private Label saveDirectoryPath;
    @FXML
    private Label complete;
    @FXML
    private ProgressIndicator progressIndicator;

    public void selectFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        List<File> files = fileChooser.showOpenMultipleDialog(getStage());
        if(files != null) {
            filesCount.setText(files.size() + " file(s)");
        } else {
            filesCount.setText("");
        }
    }

    public void selectSaveDirectory(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(getStage());
        if (file != null) {
            saveDirectoryPath.setText(file.getAbsolutePath());
        } else {
            saveDirectoryPath.setText("");
        }
    }

    private Stage getStage() {
        return (Stage) anchorPane.getScene().getWindow();
    }

    public void allIn(ActionEvent actionEvent) {

    }
}
