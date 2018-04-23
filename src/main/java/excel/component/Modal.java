package excel.component;

import excel.Util.AppProperty;
import excel.controller.ModalController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Alexander Diachenko.
 */
public class Modal {

    private final static Logger logger = Logger.getLogger(Modal.class);

    public static void openModal(Stage primaryStage, Throwable exception) {
        try {
            Stage stage = new Stage();
            stage.getIcons().add(new Image("/img/alert.png"));
            Properties properties = AppProperty.getProperty();
            FXMLLoader fxmlLoader = new FXMLLoader();
            Pane root = fxmlLoader.load(Modal.class.getResource("/view/modal.fxml").openStream());
            ModalController modalController = fxmlLoader.getController();
            Label message = modalController.getMessage();
            message.setText(exception.getMessage());
            Scene scene = new Scene(root);
            String style = (String) properties.get("style");
            scene.getStylesheets().add("/css/" + style + "/modal.css");
            stage.setScene(scene);
            stage.setTitle("ERROR!");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage.getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            logger.error(exception.getMessage(), exception);
            e.printStackTrace();
        }
    }
}
