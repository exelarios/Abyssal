package components;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class TextInput extends HBox{

    private Label label;
    private TextField textField;

    public TextInput(String formName, double textFieldWidth) {

        this.label = new Label(formName);
        this.label.getStyleClass().add("form-text");

        this.textField = new TextField();
        this.textField.setPrefWidth(textFieldWidth);
        this.textField.getStyleClass().add("form-input");
        super.getChildren().addAll(label, textField);
    }

    public String getInput() {
        return this.textField.getText();
    }

}
