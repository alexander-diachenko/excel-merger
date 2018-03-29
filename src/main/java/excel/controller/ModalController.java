package excel.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * @author Alexander Diachenko.
 */
public class ModalController {

    @FXML
    private Label message;
    @FXML
    private Button closeButton;

    public void close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public Label getMessage() {
        return message;
    }
}
