package excel;

import excel.Util.ResourceBundleControl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Alexander Diachenko.
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.getIcons().add(new Image("/img/logo.png"));
        load(primaryStage, new Locale("ru", "RU"));
    }

    public static void load(Stage primaryStage, Locale locale) throws IOException {
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.main", locale, new ResourceBundleControl());
        Parent root = FXMLLoader.load(Main.class.getResource("/view/main.fxml"), bundle);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Excel util");
        primaryStage.show();
    }
}
