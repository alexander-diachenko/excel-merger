package excel.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


/**
 * @author Alexander Diachenko.
 */
public class MergerController {

    @FXML
    public Button fromFile;

    @FXML
    public Label fromFilePath;

    public void test(ActionEvent actionEvent) {
        fromFilePath.setText("ура!");
    }
}
