package excel.controller;


import excel.Util.TimeUtil;
import excel.model.Excel;
import excel.model.ExcelImpl;
import excel.service.MergerService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


/**
 * @author Alexander Diachenko.
 */
public class MergerController {

    @FXML
    public Button open;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label fromFilePath;
    @FXML
    private TextField fromId;
    @FXML
    private TextField fromField;
    @FXML
    private TextField toId;
    @FXML
    private TextField toField;
    @FXML
    private Label toFilePath;
    @FXML
    private Label saveDirectoryPath;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Label complete;
    private File fileFrom;
    private File fileTo;
    private String savedFilePath;

    public void selectFromFile() {
        FileChooser fileFromChooser = new FileChooser();
        fileFrom = fileFromChooser.showOpenDialog(getStage());
        if (fileFrom != null) {
            fromFilePath.setText(fileFrom.getAbsolutePath());
        } else {
            fromFilePath.setText("");
        }
    }

    public void selectToFile() {
        FileChooser fileFromChooser = new FileChooser();
        fileTo = fileFromChooser.showOpenDialog(getStage());
        if (fileTo != null) {
            toFilePath.setText(fileTo.getAbsolutePath());
        } else {
            toFilePath.setText("");
        }
    }

    public void selectSaveDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File saveDirectory = directoryChooser.showDialog(getStage());
        if (saveDirectory != null) {
            saveDirectoryPath.setText(saveDirectory.getAbsolutePath());
        } else {
            saveDirectoryPath.setText("");
        }
    }

    public void merge() {
        anchorPane.setDisable(true);
        Excel excel = new ExcelImpl();
        List<Integer> fromColumns = Arrays.asList(Integer.valueOf(fromId.getText()) - 1, Integer.valueOf(fromField.getText()) - 1);
        List<Integer> toColumns = Arrays.asList(Integer.valueOf(toId.getText()) - 1, Integer.valueOf(toField.getText()) - 1);
        savedFilePath = saveDirectoryPath.getText() + "\\" + "merged_" + TimeUtil.getCurrentTime() + ".xlsx";
        MergerService mergerService = new MergerService(excel, fileFrom, fileTo, fromColumns, toColumns, savedFilePath);
        Task<Void> task = mergerService.createTask();
        progressIndicator.visibleProperty().bind(task.runningProperty());
        new Thread(task).start();
        task.setOnSucceeded(event -> {
            setComplete();

        });
        task.setOnFailed(event -> {
            setFailed(task.getException());
        });
    }

    public void open() {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(new File(savedFilePath));
            open.setDisable(true);
        } catch (IOException e) {
            setFailed(e);
            e.printStackTrace();
        }
    }

    private Stage getStage() {
        return (Stage) anchorPane.getScene().getWindow();
    }

    private void setComplete() {
        anchorPane.setDisable(false);
        open.setDisable(false);
        //TODO придумать вывод завершения
        complete.setText("DONE");
    }

    private void setFailed(Throwable exception) {
        anchorPane.setDisable(false);
        //TODO придумать вывод ошибок
        complete.setText(exception.getMessage());
    }
}
