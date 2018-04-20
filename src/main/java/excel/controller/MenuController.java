package excel.controller;

import excel.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
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
    private MenuBar menu;
    @FXML
    private MenuItem en;
    @FXML
    private MenuItem ru;
    @FXML
    private MenuItem dark;
    @FXML
    private MenuItem def;
    private ResourceBundle bundle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        init(resources);
    }

    public void languageEnAction() throws IOException {
        Alert alert = createConfirmationDialog();
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Main.load(getStage(), new Locale("en"), "dark");
        }
    }

    public void languageRuAction() throws IOException {
        Alert alert = createConfirmationDialog();
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Main.load(getStage(), new Locale("ru"), "dark");
        }
    }

    private void init(ResourceBundle bundle) {
        this.bundle = bundle;
        disableCurrentLanguage();
    }

    private void disableCurrentLanguage() {
        Locale locale = bundle.getLocale();
        String language = locale.getLanguage();
        if (language.equals("en")) {
            en.setDisable(true);
        } else if (language.equals("ru")) {
            ru.setDisable(true);
        }
    }

    private Stage getStage() {
        return (Stage) menu.getScene().getWindow();
    }

    private Alert createConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/img/question.png"));
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/css/dark/dialog.css").toExternalForm());
        alert.setTitle(bundle.getString("dialog.reload.title"));
        alert.setHeaderText(bundle.getString("dialog.reload.header"));
        alert.setContentText(bundle.getString("dialog.reload.content"));
        return alert;
    }

    public void styleDefaultAction() {
    }

    public void styleDarkAction() {
    }
}
