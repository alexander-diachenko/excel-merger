package excel.components.mergerTab.components;

import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * @author Alexander Diachenko.
 */
public class CompleteIndicatorHBox extends HBox {

    private ProgressIndicator progressIndicator;
    private Text complete;

    public CompleteIndicatorHBox() {
        init();
    }

    private void init() {
        progressIndicator = new ProgressIndicator();
        complete = new Text();
        progressIndicator.setVisible(false);
        progressIndicator.setPrefSize(20, 20);
        getChildren().addAll(complete, progressIndicator);
    }

    public ProgressIndicator getProgressIndicator() {
        return progressIndicator;
    }

    public Text getComplete() {
        return complete;
    }
}
