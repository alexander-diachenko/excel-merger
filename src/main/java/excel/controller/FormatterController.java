package excel.controller;

import excel.model.Excel;
import excel.model.ExcelImpl;
import excel.service.FormatterService;
import javafx.beans.binding.BooleanBinding;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
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
    private Button formatButton;
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
        disableTab(true);
        Excel excel = new ExcelImpl();
        FormatterService formatterService = new FormatterService(excel, files, optionsCheckBox, field.getValue(), value.getText());
        Task<Void> task = formatterService.createTask();
        progressIndicator.visibleProperty().bind(task.runningProperty());
        new Thread(task).start();
        task.setOnSucceeded(event -> setComplete());
        task.setOnFailed(event -> setFailed(task));
    }

    private void init() {
        formatButton.disableProperty().bind(getBooleanBinding());
        field.disableProperty().bind(getOptionsBooleanBinding());
        value.disableProperty().bind(getOptionsBooleanBinding());
    }

    private Stage getStage() {
        return (Stage) anchorPane.getScene().getWindow();
    }

    private void setComplete() {
        disableTab(false);
        //TODO придумать вывод завершения
        complete.setText("DONE");
    }

    private void setFailed(Task<Void> task) {
        disableTab(false);
        openModal(task.getException());
    }

    private void openModal(Throwable exception) {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader();
            Pane root = fxmlLoader.load(getClass().getResource("/view/modal.fxml").openStream());
            ModalController modalController = fxmlLoader.getController();
            Label message = modalController.getMessage();
            message.setText(exception.getMessage());
            stage.setScene(new Scene(root));
            stage.setTitle("ERROR!");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(getStage().getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
