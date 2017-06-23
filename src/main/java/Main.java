import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Arrays;
import java.util.List;


/**
 * Created by User on 22.06.2017.
 */
public class Main extends Application {

    private File fileFrom;
    private File fileTo;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ReadWriteExcel readWriteExcel = new ReadWriteExcelImpl();

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 50, 50, 50));
        vBox.setSpacing(10);

        FileChooser fileFromChooser = new FileChooser();
        Button selectFileFromButton = new Button();
        selectFileFromButton.setText("Select from file");
        selectFileFromButton.setOnAction(event -> {
            fileFrom = fileFromChooser.showOpenDialog(primaryStage);
        });
        vBox.getChildren().addAll(selectFileFromButton);

        HBox fromFieldsHBox = new HBox();
        TextField fromArticle = new TextField();
        fromArticle.setPromptText("From article");
        fromArticle.setFocusTraversable(true);
        fromArticle.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(getNumericRegex())) {
                fromArticle.setText(oldValue);
            }
        });
        TextField fromField = new TextField();
        fromField.setPromptText("From field");
        fromField.setFocusTraversable(true);
        fromField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(getNumericRegex())) {
                fromField.setText(oldValue);
            }
        });
        fromFieldsHBox.getChildren().addAll(fromArticle, fromField);
        vBox.getChildren().addAll(fromFieldsHBox);

        FileChooser fileToChooser = new FileChooser();
        Button selectFileToButton = new Button();
        selectFileToButton.setText("Select to file");
        selectFileToButton.setOnAction(event -> {
            fileTo = fileToChooser.showOpenDialog(primaryStage);
        });
        vBox.getChildren().addAll(selectFileToButton);

        HBox toFieldsHBox = new HBox();
        TextField toArticle = new TextField();
        toArticle.setPromptText("To article");
        toArticle.setFocusTraversable(true);
        toArticle.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(getNumericRegex())) {
                toArticle.setText(oldValue);
            }
        });
        TextField toField = new TextField();
        toField.setPromptText("To field");
        toField.setFocusTraversable(true);
        toField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(getNumericRegex())) {
                toField.setText(oldValue);
            }
        });
        toFieldsHBox.getChildren().addAll(toArticle, toField);
        vBox.getChildren().addAll(toFieldsHBox);

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
        vBox.getChildren().addAll(startButton);

        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private String getNumericRegex() {
        return "|[-\\+]?|[-\\+]?\\d+\\.?|[-\\+]?\\d+\\.?\\d+";
    }
}
