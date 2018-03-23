package excel.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;


/**
 * @author Alexander Diachenko.
 */
public class MergerController {

    @FXML
    private Button fromFile;
    @FXML
    private Label fromFilePath;
    @FXML
    private TextField fromId;
    @FXML
    private TextField fromField;
    @FXML
    private Button toFile;
    @FXML
    private TextField toId;
    @FXML
    private TextField toField;
    @FXML
    private Label toFilePath;
    @FXML
    private Button saveDirectory;
    @FXML
    private Label saveDirectoryPath;
    @FXML
    private Button merge;
    @FXML
    private Button open;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Label complete;

    public void test(ActionEvent actionEvent) {
        fromFilePath.setText("ура!");
    }
}
