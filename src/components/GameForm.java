package components;

import components.MenuButton;
import components.TextInput;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class GameForm extends GridPane {

    private Label title;
    private TextInput name;
    private TextInput colInput;
    private TextInput rowsInput;
    private MenuButton submit;

    public GameForm(int TranslateX, int TranslateY) {
        this.title = new Label("Abyssal Plain");
        this.title.getStyleClass().add("form-title");

        this.name = new TextInput("Name: ", 500);
        this.rowsInput = new TextInput("Rows: ", 130);
        this.colInput = new TextInput("Columns: " , 130);
        this.submit = new MenuButton("Submit", 300, 200);
        this.setStyle("-fx-background-color: black");

        this.setVgap(15);
        this.setTranslateX(260);
        this.setTranslateY(200);

        addObjects();
    }

    private void addObjects() {
        this.add(title, 0, 0);
        this.add(name, 0, 1);
        this.add(rowsInput, 0, 2);
        this.add(colInput, 1, 2);
        this.add(submit, 0, 0);

        colInput.setTranslateX(-330);
        submit.setTranslateX(440);
        submit.setTranslateY(230);


    }

    public String getName() {
        return this.name.getInput();
    }

    public String getColumn() {
        return this.colInput.getInput();
    }

    public String getRows() {
        return this.rowsInput.getInput();
    }

    public MenuButton getButton() {
        return submit;
    }

    public String toString() {
        String output = "";
        output += "Name: " + this.getName() + "\n";
        output += "Columns: " + this.getColumn() + "\n";
        output += "Rows: " + this.getRows();
        return output;
    }
}
