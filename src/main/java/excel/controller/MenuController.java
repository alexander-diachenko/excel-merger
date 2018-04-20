package excel.controller;

import excel.Main;
import excel.Util.AppProperty;
import excel.component.Alert;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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
        Optional<ButtonType> result = excel.component.Alert.openConfirmation(bundle).showAndWait();
        if (result.get() == ButtonType.OK) {
            setProperty("language", "en");
            reload(getStage());
        }
    }

    public void languageRuAction() {
        Optional<ButtonType> result = excel.component.Alert.openConfirmation(bundle).showAndWait();
        if (result.get() == ButtonType.OK) {
            setProperty("language", "ru");
            reload(getStage());
        }
    }

    public void styleDefaultAction() {
        Optional<ButtonType> result = excel.component.Alert.openConfirmation(bundle).showAndWait();
        if (result.get() == ButtonType.OK) {
            setProperty("style", "default");
            reload(getStage());
        }
    }

    public void styleDarkAction() {
        Optional<ButtonType> result = Alert.openConfirmation(bundle).showAndWait();
        if (result.get() == ButtonType.OK) {
            setProperty("style", "dark");
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

    private void setProperty(String key, String value) {
        Properties properties = AppProperty.getProperty();
        properties.setProperty(key, value);
        AppProperty.setProperties(properties);
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
