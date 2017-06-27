import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * Created by User on 22.06.2017.
 */
public class Main extends Application {

    private File fileFrom;
    private File fileTo;
    private File directory;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Excel excel = new ExcelImpl();

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 50, 50, 50));
        vBox.setSpacing(10);

        HBox fileFromHBox = new HBox();
        fileFromHBox.setSpacing(10);
        FileChooser fileFromChooser = new FileChooser();
        Button selectFileFromButton = new Button();
        Text fileFromPath = new Text();
        selectFileFromButton.setText("Select 'from' file");
        selectFileFromButton.setOnAction(event -> {
            fileFrom = fileFromChooser.showOpenDialog(primaryStage);
            if(fileFrom != null) {
                fileFromPath.setText(fileFrom.getAbsolutePath());
            }
        });
        fileFromHBox.getChildren().addAll(selectFileFromButton, fileFromPath);

        HBox fromFieldsHBox = new HBox();
        TextField fromArticle = new TextField();
        fromArticle.setPromptText("Enter 'from' id");
        fromArticle.setFocusTraversable(true);
        fromArticle.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(getNumericRegex())) {
                fromArticle.setText(oldValue);
            }
        });
        TextField fromField = new TextField();
        fromField.setPromptText("Enter 'from' field");
        fromField.setFocusTraversable(true);
        fromField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(getNumericRegex())) {
                fromField.setText(oldValue);
            }
        });
        fromFieldsHBox.getChildren().addAll(fromArticle, fromField);

        HBox fileToHBox = new HBox();
        fileToHBox.setSpacing(10);
        Text fileToPath = new Text();
        FileChooser fileToChooser = new FileChooser();
        Button selectFileToButton = new Button();
        selectFileToButton.setText("Select 'to' file");
        selectFileToButton.setOnAction(event -> {
            fileTo = fileToChooser.showOpenDialog(primaryStage);
            if(fileTo != null) {
                fileToPath.setText(fileTo.getAbsolutePath());
            }
        });
        fileToHBox.getChildren().addAll(selectFileToButton, fileToPath);

        HBox toFieldsHBox = new HBox();
        TextField toArticle = new TextField();
        toArticle.setPromptText("Enter 'to' id");
        toArticle.setFocusTraversable(true);
        toArticle.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(getNumericRegex())) {
                toArticle.setText(oldValue);
            }
        });
        TextField toField = new TextField();
        toField.setPromptText("Enter 'to' field");
        toField.setFocusTraversable(true);
        toField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(getNumericRegex())) {
                toField.setText(oldValue);
            }
        });
        toFieldsHBox.getChildren().addAll(toArticle, toField);

        HBox fileDirectoryHBox = new HBox();
        fileDirectoryHBox.setSpacing(10);
        Text fileDirectoryPath = new Text();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Button selectDirectoryButton = new Button();
        selectDirectoryButton.setText("Select directory");
        selectDirectoryButton.setOnAction(event -> {
            directory = directoryChooser.showDialog(primaryStage);
            if(directory != null) {
                fileDirectoryPath.setText(directory.getAbsolutePath());
            }
        });
        fileDirectoryHBox.getChildren().addAll(selectDirectoryButton, fileDirectoryPath);

        Text complete = new Text();

        Button startButton = new Button();
        startButton.setText("Merge");
        startButton.setOnAction(event -> {
            if (fileFrom != null && fileTo != null) {
                try {
                    List<List<Object>> from = excel.read(fileFrom.getAbsolutePath());
                    List<List<Object>> to = excel.read(fileTo.getAbsolutePath());
                    ExcelMerger excelMerger = new ExcelMergerImpl(from, to);
                    List<Integer> articles = Arrays.asList(Integer.valueOf(fromArticle.getText()) - 1, Integer.valueOf(toArticle.getText()) - 1);
                    List<Integer> fields = Arrays.asList(Integer.valueOf(fromField.getText()) - 1, Integer.valueOf(toField.getText()) - 1);
                    List<List<Object>> merged = excelMerger.mergeOneField(articles, fields);
                    excel.write(merged, directory.getPath() + "\\" + "merged_" + getCurrentTime() + ".xlsx");
                    complete.setFill(Color.GREEN);
                    complete.setText("DONE!");
                }catch (Exception e) {
                    e.printStackTrace();
                    complete.setFill(Color.RED);
                    complete.setText("ERROR!\n" + e.getMessage());
                }
            }
        });

        vBox.getChildren().addAll(fileFromHBox, fromFieldsHBox, fileToHBox, toFieldsHBox, fileDirectoryHBox, startButton, complete);

        Scene scene = new Scene(vBox);
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
