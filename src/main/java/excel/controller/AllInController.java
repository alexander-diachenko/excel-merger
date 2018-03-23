package excel.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

/**
 * @author Alexander Diachenko.
 */
public class AllInController {

    @FXML
    private Button file;
    @FXML
    private Button saveDirectory;
    @FXML
    private Button allIn;
    @FXML
    private Button open;
    @FXML
    private Label filePath;
    @FXML
    private Label saveDirectoryPath;
    @FXML
    private Label complete;
    @FXML
    private ProgressIndicator progressIndicator;
}
