package components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextAlignment;

public class Message extends Label {

    private Pane pane;

    public Message(Pane pane) {
        this.pane = pane;

        textCenter();
        this.setTranslateY(650);
        this.setStyle("-fx-text-fill: white; -fx-font-size: 15px;");
    }

    public Message(Pane pane, int posY) {
        this.pane = pane;

        textCenter();
        this.setTranslateY(posY);
        this.setStyle("-fx-text-fill: white; -fx-font-size: 15px;");
    }

    private void textCenter() {
        this.layoutXProperty().bind(pane.widthProperty().subtract(this.widthProperty()).divide(2));
    }

    public void setMessage(String text) {
        super.setText(text);
        textCenter();
    }

    public String toString() {
        return super.getText();
    }
}
