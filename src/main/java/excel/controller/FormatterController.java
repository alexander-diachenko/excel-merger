package excel.controller;

import excel.model.Excel;
import excel.model.ExcelImpl;
import excel.service.FormatterService;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
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
    private GridPane gridPane;
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
        hide(progressIndicator);
        FileChooser fileChooser = new FileChooser();
        files = fileChooser.showOpenMultipleDialog(getStage());
        if (files != null) {
            filesCount.setText(files.size() + " file(s)");
        } else {
            filesCount.setText("");
        }
    }

    public void formatAction() {
        hide(progressIndicator);
        disableTab(true);
        Excel excel = new ExcelImpl();
        FormatterService formatterService = new FormatterService(excel, files, optionsCheckBox, field.getValue(), value.getText());
        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        progressIndicator.visibleProperty().bind(formatterService.runningProperty());
        formatterService.restart();
        formatterService.setOnSucceeded(event -> setComplete());
        formatterService.setOnFailed(event -> setFailed(formatterService.getException()));
    }

    private void init() {
        format.disableProperty().bind(getBooleanBinding());
        field.disableProperty().bind(getOptionsBooleanBinding());
        value.disableProperty().bind(getOptionsBooleanBinding());
    }

    private Stage getStage() {
        return (Stage) gridPane.getScene().getWindow();
    }

    private void setComplete() {
        disableTab(false);
        progressIndicator.visibleProperty().unbind();
        progressIndicator.setProgress(1);
    }

    private void setFailed(Throwable exception) {
        disableTab(false);
        Modal.openModal(getStage(), exception);
    }

    private void disableTab(boolean value) {
        gridPane.setDisable(value);
    }

    private BooleanBinding getBooleanBinding() {
        return filesCount.textProperty().isEmpty();
    }

    private BooleanBinding getOptionsBooleanBinding() {
        return optionsCheckBox.selectedProperty().not();
    }

    private void hide(ProgressIndicator progressIndicator) {
        if(progressIndicator.isVisible()) {
            progressIndicator.setVisible(false);
        }
    }
}
