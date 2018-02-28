package excel.components.mergerTab.components;

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
    private Text fileDirectoryPath;

    public FileDirectoryHBox(final Stage primaryStage) {
        setSpacing(10);
        fileDirectoryPath = new Text();
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        final Button selectDirectoryButton = new Button();
        selectDirectoryButton.setText("Select file directory");
        selectDirectoryButton.setOnAction(event -> {
            fileDirectory = directoryChooser.showDialog(primaryStage);
            if(fileDirectory != null) {
                fileDirectoryPath.setText(fileDirectory.getAbsolutePath());
            } else {
                fileDirectoryPath.setText("");
            }
        });
        getChildren().addAll(selectDirectoryButton, fileDirectoryPath);
    }

    public File getFileDirectory() {
        return fileDirectory;
    }

    public Text getFileDirectoryPath() {
        return fileDirectoryPath;
    }
}
