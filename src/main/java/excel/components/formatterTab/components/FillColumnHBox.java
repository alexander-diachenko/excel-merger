package excel.components.formatterTab.components;

import excel.Util.RegexUtil;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * @author Alexander Diachenko.
 */
public class FillColumnHBox extends HBox {

    private final TextField columnNumber;
    private final TextField columnValue;

    public FillColumnHBox() {
        final CheckBox isChecked = new CheckBox();
        isChecked.setSelected(false);

        columnNumber = new TextField();
        columnNumber.setPromptText("Enter column number");
        columnNumber.setDisable(true);
        columnNumber.setFocusTraversable(true);
        columnNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(RegexUtil.getNumericRegex())) {
                columnNumber.setText(oldValue);
            }
        });

        columnValue = new TextField();
        columnValue.setPromptText("Enter value");
        columnValue.setDisable(true);
        columnValue.setFocusTraversable(true);

        isChecked.selectedProperty().addListener((observable, oldValue, newValue) -> {
            columnNumber.clear();
            columnValue.clear();
            columnNumber.setDisable(!newValue);
            columnValue.setDisable(!newValue);
        });

        getChildren().addAll(isChecked, columnNumber, columnValue);
    }

    public TextField getColumnNumber() {
        return columnNumber;
    }

    public TextField getColumnValue() {
        return columnValue;
    }
}
