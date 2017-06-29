package excel.components;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * @author Alexander Diachenko.
 */
public class FileToHBox extends HBox {

    private File fileTo;

    public FileToHBox(Stage primaryStage) {
        setSpacing(10);
        FileChooser fileFromChooser = new FileChooser();
        Button selectFileFromButton = new Button();
        Text fileFromPath = new Text();
        selectFileFromButton.setText("Select 'to' file");
        selectFileFromButton.setOnAction(event -> {
            fileTo = fileFromChooser.showOpenDialog(primaryStage);
            if (fileTo != null) {
                fileFromPath.setText(fileTo.getAbsolutePath());
            }
        });
        getChildren().addAll(selectFileFromButton, fileFromPath);
    }

    public File getFileTo() {
        return fileTo;
    }
}
