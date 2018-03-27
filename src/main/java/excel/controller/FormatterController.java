package excel.controller;

import excel.model.Excel;
import excel.model.ExcelImpl;
import excel.service.FormatterService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

/**
 * @author Alexander Diachenko.
 */
public class FormatterController {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label filesCount;
    @FXML
    private CheckBox optionsCheckBox;
    @FXML
    private TextField field;
    @FXML
    private TextField value;
    @FXML
    private Label complete;
    @FXML
    private ProgressIndicator progressIndicator;

    private List<File> files;

    public void selectFiles() {
        FileChooser fileChooser = new FileChooser();
        files = fileChooser.showOpenMultipleDialog(getStage());
        if (files != null) {
            filesCount.setText(files.size() + " file(s)");
        } else {
            filesCount.setText("");
        }
    }

    public void format() {
        Excel excel = new ExcelImpl();
        FormatterService formatterService = new FormatterService(excel, files, field.getText(), value.getText());
        Task<Void> task = formatterService.createTask();
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
