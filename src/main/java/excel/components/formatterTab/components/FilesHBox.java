package excel.components.formatterTab.components;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

/**
 * @author Alexander Diachenko.
 */
public class FilesHBox extends HBox {

    private List<File> files;
    private final Text selectedFilesCount;

    public FilesHBox(final Stage primaryStage) {
        setSpacing(10);
        final FileChooser fileChooser = new FileChooser();
        final Button selectFilesButton = new Button();
        selectedFilesCount = new Text();
        selectFilesButton.setText("Select files");
        selectFilesButton.setOnAction(event -> {
            files = fileChooser.showOpenMultipleDialog(primaryStage);
            if(files != null) {
                selectedFilesCount.setText(files.size() + " file(s)");
            } else {
                selectedFilesCount.setText("");
            }
        });
        getChildren().addAll(selectFilesButton, selectedFilesCount);
    }

    public List<File> getFiles() {
        return files;
    }

    public Text getSelectedFilesCount() {
        return selectedFilesCount;
    }
}
