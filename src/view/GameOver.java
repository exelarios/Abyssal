package view;

import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import components.Message;


public class GameOver extends Pane {

    private Game mainScene;
    private Message msg;
    private Message EscMsg;
    private String mainText;
    private int fontSize;

    public GameOver(Game mainScene, String mainText, int fontSize) {

        this.mainScene = mainScene;
        this.mainText = mainText;
        this.setStyle("-fx-background-color: #000000");
        this.fontSize = fontSize;

        msg = new Message(this);
        EscMsg = new Message(this, 670);
        this.getChildren().addAll(msg, EscMsg);

        addText(fontSize);
        deathMessage();
        addEscapeText();
    }

    private void addText(int fontSize) {
        Label gameOverText = new Label(mainText);
        gameOverText.getStyleClass().add("gameOver-text");
        gameOverText.setStyle("-fx-font-size: " + fontSize);
        gameOverText.layoutXProperty().bind(this.widthProperty().subtract(gameOverText.widthProperty()).divide(2));
        gameOverText.setTranslateY(300);
        this.getChildren().add(gameOverText);
    }

    private void addEscapeText() {
        EscMsg.setMessage("Press ENTER to go back to menu.");
        mainScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                mainScene.restartGame();
            }
        });
    }

    private void deathMessage() {
        if (Game.touchedTrap) {
            msg.setMessage("You felt into the abyss void.");
        }

        if (Game.playerWon) {
            msg.setMessage("You have defeated Solomon and the spell has been released.");
        }

        if (Game.playerDied) {
            mainScene.deleteData();
        }

    }


}
