package excel;

import excel.Util.AppProperty;
import excel.Util.ResourceBundleControl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @author Alexander Diachenko.
 */
public class Main extends Application {
    static{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hhmmss");
        System.setProperty("current.date", dateFormat.format(new Date()));
    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.getIcons().add(new Image("/img/logo.png"));
        Properties properties = AppProperty.getProperty();
        String language = (String) properties.get("language");
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.main", new Locale(language), new ResourceBundleControl());
        Parent root = FXMLLoader.load(Main.class.getResource("/view/main.fxml"), bundle);
        Scene scene = new Scene(root);
        String style = (String) properties.get("style");
        scene.getStylesheets().add("/css/" + style + "/main.css");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Excel util");
        primaryStage.show();
    }
}
