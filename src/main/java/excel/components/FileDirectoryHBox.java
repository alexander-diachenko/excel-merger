package excel.components;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * @author Alexander Diachenko.
 */
public class FileDirectoryHBox extends HBox {

    private File fileDirectory;

    public FileDirectoryHBox(final Stage primaryStage) {
        setSpacing(10);
        Text fileDirectoryPath = new Text();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Button selectDirectoryButton = new Button();
        selectDirectoryButton.setText("Select fileDirectory");
        selectDirectoryButton.setOnAction(event -> {
            fileDirectory = directoryChooser.showDialog(primaryStage);
            if(fileDirectory != null) {
                fileDirectoryPath.setText(fileDirectory.getAbsolutePath());
            }
        });
        getChildren().addAll(selectDirectoryButton, fileDirectoryPath);
    }

    public File getFileDirectory() {
        return fileDirectory;
    }
}
