package excel.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Alexander Diachenko.
 */
public class Modal {

    public static void openModal(Stage primaryStage, Throwable exception) {
        try {
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/img/alert.png"));
            FXMLLoader fxmlLoader = new FXMLLoader();
            Pane root = fxmlLoader.load(Modal.class.getResource("/view/modal.fxml").openStream());
            ModalController modalController = fxmlLoader.getController();
            Label message = modalController.getMessage();
            message.setText(exception.getMessage());
            stage.setScene(new Scene(root));
            stage.setTitle("ERROR!");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
