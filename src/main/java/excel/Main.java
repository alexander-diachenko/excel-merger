package excel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Alexander Diachenko.
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        String fxmlDocPath = "D:\\Soft\\JavaProjects\\excel\\src\\main\\java\\excel\\view\\main.fxml";
        FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);

        TabPane root = loader.load(fxmlStream);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Excel util");
        primaryStage.show();
    }
}
