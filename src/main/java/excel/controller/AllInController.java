package excel.controller;

import excel.model.Excel;
import excel.model.ExcelImpl;
import excel.service.AllInService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
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
    private Label filesCount;
    @FXML
    private Label saveDirectoryPath;
    @FXML
    private Label complete;
    @FXML
    private ProgressIndicator progressIndicator;

    private List<File> files;

    private File saveDirectory;

    public void selectFiles() {
        FileChooser fileChooser = new FileChooser();
        files = fileChooser.showOpenMultipleDialog(getStage());
        if (files != null) {
            filesCount.setText(files.size() + " file(s)");
        } else {
            filesCount.setText("");
        }
    }

    public void selectSaveDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        saveDirectory = directoryChooser.showDialog(getStage());
        if (saveDirectory != null) {
            saveDirectoryPath.setText(saveDirectory.getAbsolutePath());
        } else {
            saveDirectoryPath.setText("");
        }
    }

    public void allIn() {
        Excel excel = new ExcelImpl();
        AllInService allInService = new AllInService(files, excel, saveDirectoryPath.getText());
        Task<Void> task = allInService.createTask();
        progressIndicator.visibleProperty().bind(task.runningProperty());
        new Thread(task).start();
        task.setOnSucceeded(event -> {
            //TODO придумать вывод завершения
            complete.setText("DONE");
        });
        task.setOnFailed(event -> {
            //TODO придумать вывод ошибок
            complete.setText(task.getException().getMessage());
        });
    }

    private Stage getStage() {
        return (Stage) anchorPane.getScene().getWindow();
    }
}
