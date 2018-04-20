package excel.controller;

import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ResourceBundle;

/**
 * @author Alexander Diachenko.
 */
public class Alert {

    public static javafx.scene.control.Alert openConfirmation(ResourceBundle bundle) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/img/question.png"));
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Alert.class.getResource("/css/dark/dialog.css").toExternalForm());
        alert.setTitle(bundle.getString("dialog.reload.title"));
        alert.setHeaderText(bundle.getString("dialog.reload.header"));
        alert.setContentText(bundle.getString("dialog.reload.content"));
        return alert;
    }
}
