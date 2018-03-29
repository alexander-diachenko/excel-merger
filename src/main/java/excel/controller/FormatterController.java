package excel.controller;

import excel.model.Excel;
import excel.model.ExcelImpl;
import excel.service.FormatterService;
import javafx.beans.binding.BooleanBinding;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Alexander Diachenko.
 */
public class FormatterController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label filesCount;
    @FXML
    private CheckBox optionsCheckBox;
    @FXML
    private Spinner<Integer> field;
    @FXML
    private TextField value;
    @FXML
    private Label complete;
    @FXML
    private ProgressIndicator progressIndicator;
    private List<File> files;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init();
    }

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
        anchorPane.setDisable(true);
        Excel excel = new ExcelImpl();
        FormatterService formatterService = new FormatterService(excel, files, field.getValue(), value.getText());
        Task<Void> task = formatterService.createTask();
        progressIndicator.visibleProperty().bind(task.runningProperty());
        new Thread(task).start();
        task.setOnSucceeded(event -> setComplete());
        task.setOnFailed(event -> setFailed(task));
    }

    private void init() {
        field.disableProperty().bind(getBooleanBinding());
        value.disableProperty().bind(getBooleanBinding());
    }

    private Stage getStage() {
        return (Stage) anchorPane.getScene().getWindow();
    }

    private void setComplete() {
        anchorPane.setDisable(false);
        //TODO придумать вывод завершения
        complete.setText("DONE");
    }

    private void setFailed(Task<Void> task) {
        anchorPane.setDisable(false);
        //TODO придумать вывод ошибок
        complete.setText(task.getException().getMessage());
    }

    private BooleanBinding getBooleanBinding() {
        return optionsCheckBox.selectedProperty().not();
    }
}
