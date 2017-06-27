package excel.components;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * Created by User on 27.06.2017.
 */
public class ToFieldsHBox extends HBox {

    private TextField toArticle;
    private TextField toField;

    public ToFieldsHBox(final String numericRegex) {
        toArticle = new TextField();
        toArticle.setPromptText("Enter 'to' id");
        toArticle.setFocusTraversable(true);
        toArticle.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(numericRegex)) {
                toArticle.setText(oldValue);
            }
        });
        toField = new TextField();
        toField.setPromptText("Enter 'to' field");
        toField.setFocusTraversable(true);
        toField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(numericRegex)) {
                toField.setText(oldValue);
            }
        });
        getChildren().addAll(toArticle, toField);
    }

    public TextField getToArticle() {
        return toArticle;
    }

    public TextField getToField() {
        return toField;
    }
}
