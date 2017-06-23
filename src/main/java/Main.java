import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Arrays;
import java.util.List;


/**
 * Created by User on 22.06.2017.
 */
public class Main extends Application {

    private List<List<Object>> from;
    private List<List<Object>> to;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane root = new GridPane();
        ReadWriteExcel readWriteExcel = new ReadWriteExcelImpl();

        HBox fileFromHBox = new HBox();
        FileChooser fileFromChooser = new FileChooser();
        fileFromChooser.setTitle("Select file for backup");
        Button selectFileFromButton = new Button();
        selectFileFromButton.setText("Select file");
        selectFileFromButton.setOnAction(event -> {
            File fileFrom = fileFromChooser.showOpenDialog(primaryStage);
            if(fileFrom != null) {
                from = readWriteExcel.read(fileFrom.getAbsolutePath());
            }
        });
        fileFromHBox.getChildren().addAll(selectFileFromButton);

        HBox fileToHBox = new HBox();
        FileChooser fileToChooser = new FileChooser();
        fileToChooser.setTitle("Select file for backup");
        Button selectFileToButton = new Button();
        selectFileToButton.setText("Select file");
        selectFileToButton.setOnAction(event -> {
            File fileTo = fileToChooser.showOpenDialog(primaryStage);
            if(fileTo != null) {
                to = readWriteExcel.read(fileTo.getAbsolutePath());
            }
        });
        fileToHBox.getChildren().addAll(selectFileToButton);



        List<Integer> articles = Arrays.asList(0, 6);
        List<Integer> fields = Arrays.asList(1, 15);
        HBox startHBox = new HBox();
        Button startButton = new Button();
        startButton.setText("Start");
        startButton.setOnAction(event -> {
            if(from != null && to != null) {
                MergeExcel mergeExcel = new MergeExcelImpl(from, to);
                List<List<Object>> merged = mergeExcel.mergeOneField(articles, fields);
                readWriteExcel.write(merged,"D:\\Downloads\\merged.xlsx");
                new Console().write("Готово!");

            }
        });
        startHBox.getChildren().addAll(startButton);

        GridPane.setConstraints(fileFromHBox, 0, 0);
        GridPane.setConstraints(fileToHBox, 0, 1);
        GridPane.setConstraints(startHBox, 0, 2);
        root.getChildren().addAll(fileFromHBox, fileToHBox, startHBox);
        primaryStage.setTitle("Excel_merger");
        primaryStage.setScene(new Scene(root, 150, 150));
        primaryStage.show();

    }
}
