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
public class FileFromHBox extends HBox {

    private File fileFrom;

    public FileFromHBox(final Stage primaryStage) {
        setSpacing(10);
        final FileChooser fileFromChooser = new FileChooser();
        final Button selectFileFromButton = new Button();
        final Text fileFromPath = new Text();
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
