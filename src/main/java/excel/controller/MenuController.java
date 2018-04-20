package excel.controller;

import excel.Main;
import excel.Util.AppProperty;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
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

    public void languageEnAction() {
        Alert alert = createConfirmationDialog();
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Properties properties = AppProperty.getProperty();
            properties.setProperty("language", "en");
            AppProperty.setProperties(properties);
            reload(getStage());
        }
    }

    public void languageRuAction() {
        Alert alert = createConfirmationDialog();
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Properties properties = AppProperty.getProperty();
            properties.setProperty("language", "ru");
            AppProperty.setProperties(properties);
            reload(getStage());
        }
    }

    public void styleDefaultAction() {
        Alert alert = createConfirmationDialog();
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Properties properties = AppProperty.getProperty();
            properties.setProperty("style", "default");
            AppProperty.setProperties(properties);
            reload(getStage());
        }
    }

    public void styleDarkAction() {
        Alert alert = createConfirmationDialog();
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Properties properties = AppProperty.getProperty();
            properties.setProperty("style", "dark");
            AppProperty.setProperties(properties);
            reload(getStage());
        }
    }

    private void init(ResourceBundle bundle) {
        this.bundle = bundle;
        Properties properties = AppProperty.getProperty();
        disableCurrentLanguage(properties);
        disableCurrentStyle(properties);
    }

    private void disableCurrentStyle(Properties properties) {
        String style = properties.getProperty("style");
        if (style.equals("default")) {
            def.setDisable(true);
        } else if(style.equals("dark")) {
            dark.setDisable(true);
        }
    }

    private void disableCurrentLanguage(Properties properties) {
        String language = properties.getProperty("language");
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

    private void reload(Stage primaryStage) {
        primaryStage.close();
        Platform.runLater(() -> {
            try {
                new Main().start(new Stage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
