import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

    private File fileFrom;
    private File fileTo;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane root = new GridPane();
        ReadWriteExcel readWriteExcel = new ReadWriteExcelImpl();

        HBox fileFromHBox = new HBox();
        FileChooser fileFromChooser = new FileChooser();
        Button selectFileFromButton = new Button();
        selectFileFromButton.setText("Select from file");
        selectFileFromButton.setOnAction(event -> {
            fileFrom = fileFromChooser.showOpenDialog(primaryStage);
        });
        fileFromHBox.getChildren().addAll(selectFileFromButton);

        HBox fromFieldsHBox = new HBox();
        TextField fromArticle = new TextField();
        fromArticle.setPromptText("From article");
        fromArticle.setFocusTraversable(false);
        fromArticle.setPrefWidth(30);
        fromArticle.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("|[-\\+]?|[-\\+]?\\d+\\.?|[-\\+]?\\d+\\.?\\d+")) {
                fromArticle.setText(oldValue);
            }
        });
        TextField fromField = new TextField();
        fromField.setPromptText("From field");
        fromField.setFocusTraversable(false);
        fromField.setPrefWidth(30);
        fromField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("|[-\\+]?|[-\\+]?\\d+\\.?|[-\\+]?\\d+\\.?\\d+")) {
                fromField.setText(oldValue);
            }
        });
        fromFieldsHBox.getChildren().addAll(fromArticle, fromField);

        HBox fileToHBox = new HBox();
        FileChooser fileToChooser = new FileChooser();
        Button selectFileToButton = new Button();
        selectFileToButton.setText("Select to file");
        selectFileToButton.setOnAction(event -> {
            fileTo = fileToChooser.showOpenDialog(primaryStage);
        });
        fileToHBox.getChildren().addAll(selectFileToButton);

        HBox toFieldsHBox = new HBox();
        TextField toArticle = new TextField();
        fromArticle.setPromptText("To article");
        fromArticle.setFocusTraversable(false);
        fromArticle.setPrefWidth(30);
        fromArticle.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("|[-\\+]?|[-\\+]?\\d+\\.?|[-\\+]?\\d+\\.?\\d+")) {
                toArticle.setText(oldValue);
            }
        });
        TextField toField = new TextField();
        toField.setPromptText("To field");
        toField.setFocusTraversable(false);
        toField.setPrefWidth(30);
        toField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("|[-\\+]?|[-\\+]?\\d+\\.?|[-\\+]?\\d+\\.?\\d+")) {
                toField.setText(oldValue);
            }
        });
        toFieldsHBox.getChildren().addAll(toArticle, toField);

        HBox startHBox = new HBox();
        Button startButton = new Button();
        startButton.setText("Start");
        startButton.setOnAction(event -> {
            if(fileFrom != null && fileTo != null) {
                List<List<Object>> from = readWriteExcel.read(fileFrom.getAbsolutePath());
                List<List<Object>> to = readWriteExcel.read(fileTo.getAbsolutePath());
                MergeExcel mergeExcel = new MergeExcelImpl(from, to);
                List<Integer> articles = Arrays.asList(Integer.valueOf(fromArticle.getText()), Integer.valueOf(toArticle.getText()));
                List<Integer> fields = Arrays.asList(Integer.valueOf(fromField.getText()), Integer.valueOf(toField.getText()));
                List<List<Object>> merged = mergeExcel.mergeOneField(articles, fields);
                readWriteExcel.write(merged,"D:\\Downloads\\merged.xlsx");
                new Console().write("Готово!");
            }
        });
        startHBox.getChildren().addAll(startButton);

        GridPane.setConstraints(fileFromHBox, 0, 0);
        GridPane.setConstraints(fromFieldsHBox, 0, 1);
        GridPane.setConstraints(fileToHBox, 0, 2);
        GridPane.setConstraints(toFieldsHBox, 0, 3);
        GridPane.setConstraints(startHBox, 0, 4);
        root.getChildren().addAll(fileFromHBox, fromFieldsHBox, fileToHBox, toFieldsHBox,  startHBox);
        primaryStage.setTitle("Excel_merger");
        primaryStage.setScene(new Scene(root, 150, 150));
        primaryStage.show();

    }
}
