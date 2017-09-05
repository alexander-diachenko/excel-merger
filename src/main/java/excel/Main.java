package excel;

import excel.components.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


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
        Tab mergerTab = new Tab();
        mergerTab.setText("Merger");
        VBox mergerVBox = new VBox();
        mergerVBox.setPadding(new Insets(10, 50, 50, 50));
        mergerVBox.setSpacing(10);
        final FileFromHBox fileFromHBox = new FileFromHBox(primaryStage);
        final FromFieldsHBox fromFieldsHBox = new FromFieldsHBox(getNumericRegex());
        final FileToHBox fileToHBox = new FileToHBox(primaryStage);
        final ToFieldsHBox toFieldsHBox = new ToFieldsHBox(getNumericRegex());
        final FileDirectoryHBox fileDirectoryHBox = new FileDirectoryHBox(primaryStage);
        Text complete = new Text();

        Button startButton = new Button();
        startButton.setText("Merge");
        startButton.setOnAction(event -> {
            final File fileFrom = fileFromHBox.getFileFrom();
            final File fileTo = fileToHBox.getFileTo();
            final File fileDirectory = fileDirectoryHBox.getFileDirectory();
            if (fileFrom != null && fileTo != null && fileDirectory != null) {
                try {
                    Excel excel = new ExcelImpl();
                    List<List<Object>> from = excel.read(fileFrom.getAbsolutePath());
                    List<List<Object>> to = excel.read(fileTo.getAbsolutePath());
                    ExcelMerger excelMerger = new ExcelMergerImpl(from, to);
                    List<Integer> articles = Arrays.asList(Integer.valueOf(fromFieldsHBox.getFromId().getText()) - 1, Integer.valueOf(toFieldsHBox.getToId().getText()) - 1);
                    List<Integer> fields = Arrays.asList(Integer.valueOf(fromFieldsHBox.getFromField().getText()) - 1, Integer.valueOf(toFieldsHBox.getToField().getText()) - 1);
                    List<List<Object>> merged = excelMerger.mergeOneField(articles, fields);
                    excel.write(merged, fileDirectory.getPath() + "\\" + "merged_" + getCurrentTime() + ".xlsx");
                    complete.setFill(Color.GREEN);
                    complete.setText("DONE!");
                }catch (Exception e) {
                    e.printStackTrace();
                    complete.setFill(Color.RED);
                    complete.setText("ERROR!\n" + e.getMessage());
                }
            }
        });

        mergerVBox.getChildren().addAll(fileFromHBox, fromFieldsHBox, fileToHBox, toFieldsHBox, fileDirectoryHBox, startButton, complete);
        mergerTab.setContent(mergerVBox);
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

    private String getCurrentTime() {
        return new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss").format(new Date());
    }

    private String getNumericRegex() {
        return "|[+]?|[+]?\\d+\\.?|[+]?\\d+\\.?\\d+";
    }
}
