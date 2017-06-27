package excel.components;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * Created by User on 27.06.2017.
 */
public class FromFieldsHBox extends HBox {

    private TextField fromArticle;
    private TextField fromField;

    public FromFieldsHBox(final String numericRegex) {
        fromArticle = new TextField();
        fromArticle.setPromptText("Enter 'from' id");
        fromArticle.setFocusTraversable(true);
        fromArticle.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(numericRegex)) {
                fromArticle.setText(oldValue);
            }
        });
        fromField = new TextField();
        fromField.setPromptText("Enter 'from' field");
        fromField.setFocusTraversable(true);
        fromField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(numericRegex)) {
                fromField.setText(oldValue);
            }
        });
        getChildren().addAll(fromArticle, fromField);
    }

    public TextField getFromArticle() {
        return fromArticle;
    }

    public TextField getFromField() {
        return fromField;
    }
}
