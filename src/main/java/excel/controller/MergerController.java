package excel.controller;


import excel.Util.FileUtil;
import excel.Util.TimeUtil;
import excel.model.Excel;
import excel.model.ExcelImpl;
import excel.service.MergerService;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


/**
 * @author Alexander Diachenko.
 */
public class MergerController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label fromFilePath;
    @FXML
    private Spinner<Integer> fromId;
    @FXML
    private Spinner<Integer> fromField;
    @FXML
    private Spinner<Integer> toId;
    @FXML
    private Spinner<Integer> toField;
    @FXML
    private Label toFilePath;
    @FXML
    private Label saveDirectoryPath;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Button merge;
    @FXML
    private Button open;
    private File fileFrom;
    private File fileTo;
    private String savedFilePath;

    @Override
    public void initialize(URL location, ResourceBundle bundle) {
        init();
    }

    public void fromFileAction() {
        refresh(progressIndicator);
        FileChooser fileFromChooser = new FileChooser();
        fileFrom = fileFromChooser.showOpenDialog(getStage());
        if (fileFrom != null) {
            fromFilePath.setText(fileFrom.getAbsolutePath());
        } else {
            fromFilePath.setText("");
        }
    }

    public void toFileAction() {
        refresh(progressIndicator);
        FileChooser fileFromChooser = new FileChooser();
        fileTo = fileFromChooser.showOpenDialog(getStage());
        if (fileTo != null) {
            toFilePath.setText(fileTo.getAbsolutePath());
        } else {
            toFilePath.setText("");
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

    public void mergeAction() {
        refresh(progressIndicator);
        disableTab(true);
        Excel excel = new ExcelImpl();
        List<Integer> fromColumns = getFromColumns();
        List<Integer> toColumns = getToColumns();
        savedFilePath = getSavedFilePath();
        MergerService mergerService = new MergerService(excel, fileFrom, fileTo, fromColumns, toColumns, savedFilePath);
        Task<Void> task = mergerService.createTask();
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

    private List<Integer> getFromColumns() {
        return Arrays.asList(fromId.getValue() - 1, fromField.getValue() - 1);
    }

    private List<Integer> getToColumns() {
        return Arrays.asList(toId.getValue() - 1, toField.getValue() - 1);
    }

    private String getSavedFilePath() {
        return saveDirectoryPath.getText() + "\\" + "merged_" + TimeUtil.getCurrentTime() + ".xlsx";
    }

    private void init() {
        merge.disableProperty().bind(getBooleanBinding());
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
        return fromFilePath.textProperty().isEmpty()
                .or(toFilePath.textProperty().isEmpty())
                .or(saveDirectoryPath.textProperty().isEmpty());
    }

    private void refresh(ProgressIndicator progressIndicator) {
        progressIndicator.visibleProperty().unbind();
        progressIndicator.setProgress(-1);
    }
}
