package excel.components;

import excel.Excel;
import excel.ExcelImpl;
import excel.ExcelMerger;
import excel.ExcelMergerImpl;
import excel.Util.RegexUtil;
import excel.Util.TimeUtil;
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
public class MergerTab extends Tab {

    public MergerTab(Stage primaryStage) {
        setText("Merger");
        VBox mergerVBox = new VBox();
        mergerVBox.setPadding(new Insets(10, 50, 50, 50));
        mergerVBox.setSpacing(10);
        final FileFromHBox fileFromHBox = new FileFromHBox(primaryStage);
        final FromFieldsHBox fromFieldsHBox = new FromFieldsHBox(RegexUtil.getNumericRegex());
        final FileToHBox fileToHBox = new FileToHBox(primaryStage);
        final ToFieldsHBox toFieldsHBox = new ToFieldsHBox(RegexUtil.getNumericRegex());
        final FileDirectoryHBox fileDirectoryHBox = new FileDirectoryHBox(primaryStage);
        Text complete = new Text();

        Button startButton = new Button();
        startButton.setText("Merge");
        startButton.setOnAction(event -> {
            final File fileFrom = fileFromHBox.getFileFrom();
            final File fileTo = fileToHBox.getFileTo();
            final File fileDirectory = fileDirectoryHBox.getFileDirectory();
            if (fileFrom != null && fileTo != null && fileDirectory != null) {
                try {
                    Excel excel = new ExcelImpl();
                    List<List<Object>> from = excel.read(fileFrom.getAbsolutePath());
                    List<List<Object>> to = excel.read(fileTo.getAbsolutePath());
                    ExcelMerger excelMerger = new ExcelMergerImpl(from, to);
                    List<Integer> articles = Arrays.asList(Integer.valueOf(fromFieldsHBox.getFromId().getText()) - 1, Integer.valueOf(toFieldsHBox.getToId().getText()) - 1);
                    List<Integer> fields = Arrays.asList(Integer.valueOf(fromFieldsHBox.getFromField().getText()) - 1, Integer.valueOf(toFieldsHBox.getToField().getText()) - 1);
                    List<List<Object>> merged = excelMerger.mergeOneField(articles, fields);
                    excel.write(merged, fileDirectory.getPath() + "\\" + "merged_" + TimeUtil.getCurrentTime() + ".xlsx");
                    complete.setFill(Color.GREEN);
                    complete.setText("DONE!");
                }catch (Exception e) {
                    e.printStackTrace();
                    complete.setFill(Color.RED);
                    complete.setText("ERROR!\n" + e.getMessage());
                }
            }
        });

        mergerVBox.getChildren().addAll(fileFromHBox, fromFieldsHBox, fileToHBox, toFieldsHBox, fileDirectoryHBox, startButton, complete);
        setContent(mergerVBox);
    }
}
