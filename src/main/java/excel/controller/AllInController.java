package excel.controller;

import excel.Util.FileUtil;
import excel.Util.TimeUtil;
import excel.model.Excel;
import excel.model.ExcelImpl;
import excel.service.AllInService;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Alexander Diachenko.
 */
public class AllInController implements Initializable{

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label filesCount;
    @FXML
    private Label saveDirectoryPath;
    @FXML
    private Button allIn;
    @FXML
    private Button open;
    @FXML
    private ProgressIndicator progressIndicator;
    private List<File> files;
    private String savedFilePath;

    @Override
    public void initialize(URL location, ResourceBundle bundle) {
        init();
    }

    public void fileAction() {
        refresh(progressIndicator);
        FileChooser fileChooser = new FileChooser();
        files = fileChooser.showOpenMultipleDialog(getStage());
        if (files != null) {
            filesCount.setText(files.size() + " file(s)");
        } else {
            filesCount.setText("");
        }
    }

    public void directoryAction() {
        refresh(progressIndicator);
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File saveDirectory = directoryChooser.showDialog(getStage());
        if (saveDirectory != null) {
            saveDirectoryPath.setText(saveDirectory.getAbsolutePath());
        } else {
            saveDirectoryPath.setText("");
        }
    }

    public void allInAction() {
        refresh(progressIndicator);
        disableTab(true);
        Excel excel = new ExcelImpl();
        savedFilePath = getSavedFilePath();
        AllInService allInService = new AllInService(files, excel, savedFilePath);
        Task<Void> task = allInService.createTask();
        progressIndicator.visibleProperty().bind(task.runningProperty());
        new Thread(task).start();
        task.setOnSucceeded(event -> setComplete());
        task.setOnFailed(event -> setFailed(task.getException()));
    }

    public void openAction() {
        open.setDisable(true);
        try {
            FileUtil.open(new File(savedFilePath));
        } catch (IOException e) {
            setFailed(e);
            e.printStackTrace();
        }
    }

    private String getSavedFilePath() {
        return saveDirectoryPath.getText() + "\\" + "AllIn_" + TimeUtil.getCurrentTime() + ".xlsx";
    }

    private void init() {
        allIn.disableProperty().bind(getBooleanBinding());
    }

    private Stage getStage() {
        return (Stage) anchorPane.getScene().getWindow();
    }

    private void setComplete() {
        disableTab(false);
        open.setDisable(false);
        progressIndicator.visibleProperty().bind(new SimpleBooleanProperty(true));
        progressIndicator.setProgress(1);
    }

    private void setFailed(Throwable exception) {
        disableTab(false);
        Modal.openModal(getStage(), exception);
    }

    private void disableTab(boolean value) {
        anchorPane.setDisable(value);
    }

    private BooleanBinding getBooleanBinding() {
        return filesCount.textProperty().isEmpty()
                .or(saveDirectoryPath.textProperty().isEmpty());
    }

    private void refresh(ProgressIndicator progressIndicator) {
        progressIndicator.visibleProperty().unbind();
        progressIndicator.setProgress(-1);
    }
}
