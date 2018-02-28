package excel.components.mergerTab;

import excel.components.mergerTab.mergeThread.ExcelMergeWriteThread;
import excel.model.*;
import excel.Util.RegexUtil;
import excel.Util.TimeUtil;
import excel.components.mergerTab.components.*;
import javafx.beans.binding.BooleanBinding;
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
public class MergerTab extends Tab implements Observer {

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
        startButton.disableProperty().bind(getBooleanBinding());
        startButton.setOnAction(event -> {
            setAllDisable(true);
            final File fileFrom = fileFromHBox.getFileFrom();
            final File fileTo = fileToHBox.getFileTo();
            final File fileDirectory = fileDirectoryHBox.getFileDirectory();
            if (fileFrom != null && fileTo != null && fileDirectory != null) {
                final List<Integer> fromColumns = Arrays.asList(Integer.valueOf(fromFieldsHBox.getFromId().getText()) - 1, Integer.valueOf(fromFieldsHBox.getFromField().getText()) - 1);
                final List<Integer> toColumns = Arrays.asList(Integer.valueOf(toFieldsHBox.getToId().getText()) - 1, Integer.valueOf(toFieldsHBox.getToField().getText()) - 1);
                logicInNewThread(fileFrom, fileTo, fileDirectory, fromColumns, toColumns);
            }
        });

        mergerVBox.getChildren().addAll(fileFromHBox, fromFieldsHBox, fileToHBox, toFieldsHBox, fileDirectoryHBox, startButton, complete);
        setContent(mergerVBox);
    }

    private void logicInNewThread(final File fileFrom, final File fileTo, final File fileDirectory, final List<Integer> articles, final List<Integer> fields) {
        final Excel excel = new ExcelImpl();
        excelMergeWriteThread = new ExcelMergeWriteThread(excel, articles, fields, fileFrom, fileTo, fileDirectory.getPath() + "\\" + "merged_" + TimeUtil.getCurrentTime() + ".xlsx");
        excelMergeWriteThread.registerObserver(this);
        new Thread(excelMergeWriteThread).start();
        setComplete(Color.YELLOWGREEN, "Working...");
    }

    @Override
    public void update() {
        excelMergeWriteThread.removeObserver(this);
        setAllDisable(false);
        startButton.disableProperty().bind(getBooleanBinding());
        setComplete(excelMergeWriteThread.getTextColor(), excelMergeWriteThread.getText());
    }

    private void setComplete(final Color color, final String message) {
        complete.setFill(color);
        complete.setText(message);
    }

    private void setAllDisable(final boolean value) {
        fileFromHBox.setDisable(value);
        fromFieldsHBox.setDisable(value);
        fileToHBox.setDisable(value);
        toFieldsHBox.setDisable(value);
        fileDirectoryHBox.setDisable(value);
        startButton.disableProperty().bind(new BooleanBinding() {
            @Override
            protected boolean computeValue() {
                return true;
            }
        });
    }

    private BooleanBinding getBooleanBinding() {
        return fileFromHBox.getFileFromPath().textProperty().isEmpty()
                .or(fromFieldsHBox.getFromId().textProperty().isEmpty())
                .or(fileToHBox.getFileToPath().textProperty().isEmpty())
                .or(fileDirectoryHBox.getFileDirectoryPath().textProperty().isEmpty());
    }
}
