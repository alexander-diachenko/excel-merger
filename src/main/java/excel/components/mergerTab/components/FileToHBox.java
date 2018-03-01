package excel.components.mergerTab.components;

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
    private final Text fileToPath;

    public FileToHBox(final Stage primaryStage) {
        setSpacing(10);
        final FileChooser fileFromChooser = new FileChooser();
        final Button selectFileFromButton = new Button();
        fileToPath = new Text();
        selectFileFromButton.setText("Select 'to' file");
        selectFileFromButton.setOnAction(event -> {
            fileTo = fileFromChooser.showOpenDialog(primaryStage);
            if (fileTo != null) {
                fileToPath.setText(fileTo.getAbsolutePath());
            } else {
                fileToPath.setText("");
            }
        });
        getChildren().addAll(selectFileFromButton, fileToPath);
    }

    public File getFileTo() {
        return fileTo;
    }

    public Text getFileToPath() {
        return fileToPath;
    }
}
