package components;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MenuButton extends GridPane {

    public MenuButton(String text, double posX, double posY) {
        addText(text);
        addArrow();
        this.setHgap(20);
        this.setTranslateX(posX);
        this.setTranslateY(posY);
    }

    private void addArrow() {
        Image arrowImg = new Image("file:././assets/images/arrow.png", 20, 20, true, true);
        ImageView arrowView = new ImageView(arrowImg);
        arrowView.setVisible(false);
        this.add(arrowView, 1, 0);

        this.setOnMouseEntered(event -> {
            arrowView.setVisible(true);
        });

        this.setOnMouseExited(event -> {
            arrowView.setVisible(false);
        });

    }

    public void addText(String text) {
        Text txt = new Text(text.toUpperCase());
        txt.setFill(Color.WHITE);
        txt.getStyleClass().add("menuBtn-text");
        txt.setStyle("-fx-font-weight: bold; -fx-font-size: 40px;");
        this.add(txt, 2, 0);
    }
}