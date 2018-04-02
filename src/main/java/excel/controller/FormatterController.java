package excel.controller;

import excel.model.Excel;
import excel.model.ExcelImpl;
import excel.service.FormatterService;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
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
    private Button format;
    @FXML
    private ProgressIndicator progressIndicator;
    private List<File> files;

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

    public void formatAction() {
        refresh(progressIndicator);
        disableTab(true);
        Excel excel = new ExcelImpl();
        FormatterService formatterService = new FormatterService(excel, files, optionsCheckBox, field.getValue(), value.getText());
        Task<Void> task = formatterService.createTask();
        progressIndicator.visibleProperty().bind(task.runningProperty());
        new Thread(task).start();
        task.setOnSucceeded(event -> setComplete());
        task.setOnFailed(event -> setFailed(task.getException()));
    }

    private void init() {
        format.disableProperty().bind(getBooleanBinding());
        field.disableProperty().bind(getOptionsBooleanBinding());
        value.disableProperty().bind(getOptionsBooleanBinding());
    }

    private Stage getStage() {
        return (Stage) anchorPane.getScene().getWindow();
    }

    private void setComplete() {
        disableTab(false);
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
        return filesCount.textProperty().isEmpty();
    }

    private BooleanBinding getOptionsBooleanBinding() {
        return optionsCheckBox.selectedProperty().not();
    }

    private void refresh(ProgressIndicator progressIndicator) {
        progressIndicator.visibleProperty().unbind();
        progressIndicator.setProgress(-1);
    }
}
