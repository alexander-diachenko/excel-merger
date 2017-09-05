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

    public FileToHBox(final Stage primaryStage) {
        setSpacing(10);
        final FileChooser fileFromChooser = new FileChooser();
        final Button selectFileFromButton = new Button();
        final Text fileFromPath = new Text();
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
