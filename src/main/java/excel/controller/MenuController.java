package excel.controller;

import excel.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Alexander Diachenko.
 */
public class MenuController implements Initializable {

    @FXML
    public MenuBar menu;
    private ResourceBundle bundle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init(resources);
    }

    public void languageEnAction() throws IOException {
        Alert alert = createConfirmationDialog();
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Main.load(getStage(), new Locale("en"));
        }
    }

    public void languageRuAction() throws IOException {
        Alert alert = createConfirmationDialog();
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Main.load(getStage(), new Locale("ru"));
        }
    }

    private void init(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    private Stage getStage() {
        return (Stage) menu.getScene().getWindow();
    }

    private Alert createConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(bundle.getString("dialog.reload.title"));
        alert.setHeaderText(bundle.getString("dialog.reload.header"));
        alert.setContentText(bundle.getString("dialog.reload.content"));
        return alert;
    }
}
