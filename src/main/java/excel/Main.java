package excel;

import excel.components.formatterTab.FormatterTab;
import excel.components.mergerTab.MergerTab;
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
        final Group root = new Group();
        final Scene scene = new Scene(root, 400, 300, Color.WHITE);

        final TabPane tabPane = new TabPane();
        final BorderPane mainPane = new BorderPane();

        //merger tab
        final Tab mergerTab = new MergerTab(primaryStage);

        //formatter tab
        final Tab formatterTab = new FormatterTab();

        tabPane.getTabs().addAll(mergerTab, formatterTab);
        mainPane.setCenter(tabPane);
        mainPane.prefHeightProperty().bind(scene.heightProperty());
        mainPane.prefWidthProperty().bind(scene.widthProperty());

        root.getChildren().add(mainPane);
        primaryStage.setTitle("Excel utils");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
