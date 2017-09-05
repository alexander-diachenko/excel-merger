package excel;

import excel.components.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Alexander Diachenko.
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 400, 300, Color.WHITE);

        TabPane tabPane = new TabPane();
        BorderPane mainPane = new BorderPane();

        //merger tab
        MergerTab mergerTab = new MergerTab(primaryStage);
        tabPane.getTabs().add(mergerTab);

        //formatter tab
        Tab formatterTab = new Tab();
        formatterTab.setText("Formatter");
        tabPane.getTabs().add(formatterTab);

        mainPane.setCenter(tabPane);
        mainPane.prefHeightProperty().bind(scene.heightProperty());
        mainPane.prefWidthProperty().bind(scene.widthProperty());

        root.getChildren().add(mainPane);
        primaryStage.setTitle("Excel utils");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
