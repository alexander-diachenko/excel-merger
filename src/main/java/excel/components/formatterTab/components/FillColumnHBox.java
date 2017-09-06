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
    private final TextField fill;

    public FillColumnHBox() {
        final CheckBox isFill = new CheckBox();
        isFill.setSelected(false);

        columnNumber = new TextField();
        columnNumber.setPromptText("Enter column number");
        columnNumber.setDisable(true);
        columnNumber.setFocusTraversable(true);
        columnNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(RegexUtil.getNumericRegex())) {
                columnNumber.setText(oldValue);
            }
        });

        fill = new TextField();
        fill.setPromptText("Enter what to fill");
        fill.setDisable(true);
        fill.setFocusTraversable(true);

        isFill.selectedProperty().addListener((observable, oldValue, newValue) -> {
            columnNumber.clear();
            fill.clear();
            columnNumber.setDisable(!newValue);
            fill.setDisable(!newValue);
        });

        getChildren().addAll(isFill, columnNumber, fill);
    }

    public TextField getColumnNumber() {
        return columnNumber;
    }

    public TextField getFill() {
        return fill;
    }
}
