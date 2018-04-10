package excel.controller;

import excel.Main;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

/**
 * @author Alexander Diachenko.
 */
public class MenuController {

    @FXML
    public MenuBar menu;

    public void languageEnAction() throws IOException {
        Main.load(getStage(), new Locale("en", "EN"));
    }

    public void languageRuAction() throws IOException {
        Main.load(getStage(), new Locale("ru", "RU"));
    }

    private Stage getStage() {
        return (Stage) menu.getScene().getWindow();
    }
}
