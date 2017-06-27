package excel.components;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by User on 27.06.2017.
 */
public class FileFromHBox extends HBox {

    private File fileFrom;

    public FileFromHBox(Stage primaryStage) {
        setSpacing(10);
        FileChooser fileFromChooser = new FileChooser();
        Button selectFileFromButton = new Button();
        Text fileFromPath = new Text();
        selectFileFromButton.setText("Select 'from' file");
        selectFileFromButton.setOnAction(event -> {
            fileFrom = fileFromChooser.showOpenDialog(primaryStage);
            if (fileFrom != null) {
                fileFromPath.setText(fileFrom.getAbsolutePath());
            }
        });
        getChildren().addAll(selectFileFromButton, fileFromPath);
    }

    public File getFileFrom() {
        return fileFrom;
    }
}
