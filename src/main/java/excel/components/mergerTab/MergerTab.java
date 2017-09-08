package excel.components.mergerTab;

import excel.Util.ThreadListener;
import excel.model.*;
import excel.Util.RegexUtil;
import excel.Util.TimeUtil;
import excel.components.mergerTab.components.*;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author Alexander Diachenko.
 */
public class MergerTab extends Tab implements ThreadListener {

    private FileFromHBox fileFromHBox;
    private FromFieldsHBox fromFieldsHBox;
    private FileToHBox fileToHBox;
    private ToFieldsHBox toFieldsHBox;
    private FileDirectoryHBox fileDirectoryHBox;
    private Button startButton;
    private final Text complete = new Text();

    private ExcelMergeWriteThread excelMergeWriteThread;

    public MergerTab(final Stage primaryStage) {
        setText("Merger");
        final VBox mergerVBox = new VBox();
        mergerVBox.setPadding(new Insets(10, 50, 50, 50));
        mergerVBox.setSpacing(10);
        fileFromHBox = new FileFromHBox(primaryStage);
        fromFieldsHBox = new FromFieldsHBox(RegexUtil.getNumericRegex());
        fileToHBox = new FileToHBox(primaryStage);
        toFieldsHBox = new ToFieldsHBox(RegexUtil.getNumericRegex());
        fileDirectoryHBox = new FileDirectoryHBox(primaryStage);

        startButton = new Button();
        startButton.setText("Merge");
        startButton.setOnAction(event -> {
            setAllDisable(true);
            final File fileFrom = fileFromHBox.getFileFrom();
            final File fileTo = fileToHBox.getFileTo();
            final File fileDirectory = fileDirectoryHBox.getFileDirectory();
            if (fileFrom != null && fileTo != null && fileDirectory != null) {
                final List<Integer> articles = Arrays.asList(Integer.valueOf(fromFieldsHBox.getFromId().getText()) - 1, Integer.valueOf(toFieldsHBox.getToId().getText()) - 1);
                final List<Integer> fields = Arrays.asList(Integer.valueOf(fromFieldsHBox.getFromField().getText()) - 1, Integer.valueOf(toFieldsHBox.getToField().getText()) - 1);
                logic(fileFrom, fileTo, fileDirectory, articles, fields);
            }
        });

        mergerVBox.getChildren().addAll(fileFromHBox, fromFieldsHBox, fileToHBox, toFieldsHBox, fileDirectoryHBox, startButton, complete);
        setContent(mergerVBox);
    }

    private void logic(File fileFrom, File fileTo, File fileDirectory, List<Integer> articles, List<Integer> fields) {
        final Excel excel = new ExcelImpl();
        excelMergeWriteThread = new ExcelMergeWriteThread(excel, articles, fields, fileFrom, fileTo, fileDirectory.getPath() + "\\" + "merged_" + TimeUtil.getCurrentTime() + ".xlsx");
        excelMergeWriteThread.addListener(this);
        new Thread(excelMergeWriteThread).start();
        setComplete(Color.YELLOWGREEN, "Working...");
    }

    @Override
    public void notifyOfThread(Thread thread) {
        setAllDisable(false);
        setComplete(excelMergeWriteThread.getTextColor(), excelMergeWriteThread.getText());
    }

    private void setComplete(Color color, String message) {
        complete.setFill(color);
        complete.setText(message);
    }

    private void setAllDisable(boolean value) {
        fileFromHBox.setDisable(value);
        fromFieldsHBox.setDisable(value);
        fileToHBox.setDisable(value);
        toFieldsHBox.setDisable(value);
        fileDirectoryHBox.setDisable(value);
        startButton.setDisable(value);
    }
}
