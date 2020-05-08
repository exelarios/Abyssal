package main;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class GameOver extends Pane {

    private Game mainScene;
    private Message msg;
    private String mainText;

    public GameOver(Game mainScene, String mainText) {

        this.mainScene = mainScene;
        this.mainText = mainText;
        this.setStyle("-fx-background-color: #000000");

        msg = new Message(this);
        this.getChildren().add(msg);

        addText();
        deathMessage();
    }

    public void addText() {
        Label gameOverText = new Label(mainText);
        gameOverText.getStyleClass().add("gameOver-text");
        gameOverText.layoutXProperty().bind(this.widthProperty().subtract(gameOverText.widthProperty()).divide(2));
        gameOverText.setTranslateY(300);
        this.getChildren().add(gameOverText);
    }

    public void deathMessage() {
        if (Game.touchedTrap) {
            msg.setMessage("You felt into the abyss void.");
        }

        if (Game.playerWon) {
            msg.setMessage("You have defeated Solomon and the spell has been released.");
        }
    }
}
