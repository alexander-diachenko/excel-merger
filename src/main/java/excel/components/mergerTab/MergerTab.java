package excel.components.mergerTab;

import excel.components.mergerTab.mergeThread.ExcelMergeWriteThread;
import excel.model.*;
import excel.Util.RegexUtil;
import excel.Util.TimeUtil;
import excel.components.mergerTab.components.*;
import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
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
    private HBox mergeOpenHBox;
    private Button mergeButton;
    private Button openButton;
    private CompleteIndicatorHBox completeIndicatorHBox;
    private ProgressIndicator progressIndicator;
    private Text complete;
    private String path;

    private ExcelMergeWriteThread excelMergeWriteThread;

    public MergerTab(final Stage primaryStage) {
        init(primaryStage);
    }

    private void init(Stage primaryStage) {
        setText("Merger");
        final VBox mergerVBox = new VBox();
        setMergeVBoxOptions(mergerVBox);

        createElements(primaryStage);

        mergeButton.disableProperty().bind(getBooleanBinding());
        mergeButton.setOnAction(event -> mergeButtonActions());
        openButton.setOnAction(event -> openButtonActions());
        mergerVBox.getChildren().addAll(fileFromHBox, fromFieldsHBox, fileToHBox, toFieldsHBox, fileDirectoryHBox, mergeOpenHBox, completeIndicatorHBox);
        setContent(mergerVBox);
    }

    private void setMergeVBoxOptions(VBox vBox) {
        vBox.setPadding(new Insets(10, 50, 50, 50));
        vBox.setSpacing(10);
    }

    private void createElements(Stage primaryStage) {
        fileFromHBox = new FileFromHBox(primaryStage);
        fromFieldsHBox = new FromFieldsHBox(RegexUtil.getNumericRegex());
        fileToHBox = new FileToHBox(primaryStage);
        toFieldsHBox = new ToFieldsHBox(RegexUtil.getNumericRegex());
        fileDirectoryHBox = new FileDirectoryHBox(primaryStage);
        mergeOpenHBox = createMergeOpenHBox();
        completeIndicatorHBox = new CompleteIndicatorHBox();
        progressIndicator = completeIndicatorHBox.getProgressIndicator();
        complete = completeIndicatorHBox.getComplete();
    }

    private HBox createMergeOpenHBox() {
        HBox mergeOpenHBox = new HBox(10);
        mergeButton = new Button("Merge");
        openButton = new Button("Open");
        openButton.setDisable(true);
        mergeOpenHBox.getChildren().addAll(mergeButton, openButton);
        return mergeOpenHBox;
    }

    private void mergeButtonActions() {
        setAllDisable(true);
        final File fileFrom = fileFromHBox.getFileFrom();
        final File fileTo = fileToHBox.getFileTo();
        final File fileDirectory = fileDirectoryHBox.getFileDirectory();
        final List<Integer> fromColumns = Arrays.asList(Integer.valueOf(fromFieldsHBox.getFromId().getText()) - 1, Integer.valueOf(fromFieldsHBox.getFromField().getText()) - 1);
        final List<Integer> toColumns = Arrays.asList(Integer.valueOf(toFieldsHBox.getToId().getText()) - 1, Integer.valueOf(toFieldsHBox.getToField().getText()) - 1);
        logicInNewThread(fileFrom, fileTo, fileDirectory, fromColumns, toColumns);
        progressIndicator.setVisible(true);
    }

    private void setAllDisable(final boolean value) {
        fileFromHBox.setDisable(value);
        fromFieldsHBox.setDisable(value);
        fileToHBox.setDisable(value);
        toFieldsHBox.setDisable(value);
        fileDirectoryHBox.setDisable(value);
        mergeButton.disableProperty().bind(new BooleanBinding() {
            @Override
            protected boolean computeValue() {
                return true;
            }
        });
    }

    private void logicInNewThread(final File fileFrom, final File fileTo, final File fileDirectory, final List<Integer> articles, final List<Integer> fields) {
        Excel excel = new ExcelImpl();
        path = fileDirectory.getPath() + "\\" + "merged_" + TimeUtil.getCurrentTime() + ".xlsx";
        excelMergeWriteThread = new ExcelMergeWriteThread(excel, articles, fields, fileFrom, fileTo, path);
        excelMergeWriteThread.registerObserver(this);
        new Thread(excelMergeWriteThread).start();
        setComplete(Color.YELLOWGREEN, "Working...");
    }

    @Override
    public void update() {
        excelMergeWriteThread.removeObserver(this);
        progressIndicator.setVisible(false);
        setAllDisable(false);
        mergeButton.disableProperty().bind(getBooleanBinding());
        setComplete(excelMergeWriteThread.getTextColor(), excelMergeWriteThread.getText());
        if(excelMergeWriteThread.getTextColor().equals(Color.GREEN)) {
            openButton.setDisable(false);
        }
    }

    private void openButtonActions() {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(new File(path));
            openButton.setDisable(true);
        } catch (IOException e) {
            setComplete(Color.RED, e.getMessage());
            e.printStackTrace();
        }
    }

    private void setComplete(final Color color, final String message) {
        complete.setFill(color);
        complete.setText(message);
    }

    private BooleanBinding getBooleanBinding() {
        return fileFromHBox.getFileFromPath().textProperty().isEmpty()
                .or(fromFieldsHBox.getFromId().textProperty().isEmpty())
                .or(fromFieldsHBox.getFromField().textProperty().isEmpty())
                .or(fileToHBox.getFileToPath().textProperty().isEmpty())
                .or(toFieldsHBox.getToId().textProperty().isEmpty())
                .or(toFieldsHBox.getToField().textProperty().isEmpty())
                .or(fileDirectoryHBox.getFileDirectoryPath().textProperty().isEmpty());
    }
}
