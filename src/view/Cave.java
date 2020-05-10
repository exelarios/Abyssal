package view;

import entity.Player;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import components.Message;

public class Cave extends Pane {

    private Rectangle topDoor;
    private Rectangle bottomDoor;
    private Rectangle leftDoor;
    private Rectangle rightDoor;
    private Player player;
    private int posX;
    private int posY;
    private Game gameScene;
    private Message msg;
    private GridPane livesPane;
    private boolean isCurseActive;
    private boolean isPowerUpActive;
    private boolean touchedTrap;
    private Label ammoLabel;

    public Cave(Game gameScene, Player player, int posX, int posY) {
        this.gameScene = gameScene;
        this.posX = posX;
        this.posY = posY;

        this.isCurseActive = false;
        this.isPowerUpActive = false;
        this.touchedTrap = false;

        Label positionLabel = new Label("(" + this.posX + ", " + this.posY + ")" );
        positionLabel.setStyle("-fx-font-size: 20px");
        positionLabel.setTextFill(Color.color(Math.random(), Math.random(), Math.random()));

        msg = new Message(this);

        this.getChildren().addAll(msg);

        this.getStyleClass().add("caves");
        displayAmmo();
        displayLives();
        displaySaveButton();
        addDoors();
        removeDoors();

    }

    public GridPane getLivesPane() {
        return this.livesPane;
    }

    public boolean getIsPowerUpActive() {
        return this.isPowerUpActive;
    }

    public void setIsPowerIsActive(boolean bool) {
        this.isPowerUpActive = bool;
    }

    public boolean getTouchedTrap() {
        return this.touchedTrap;
    }

    public void setTouchedTrap(boolean bool) {
        this.touchedTrap = bool;
    }

    public Label getDisplayAmmo() {
        return ammoLabel;
    }

    public void displayAmmo() {

        ammoLabel = new Label("Ammo: " + gameScene.getAmmo());
        ammoLabel.setPadding(new Insets(10,10,10,10));
        ammoLabel.setStyle("-fx-text-fill: white; -fx-font-size: 30px");
        ammoLabel.setTextAlignment(TextAlignment.RIGHT);
        ammoLabel.setTranslateX(gameScene.getWidth() - 120);

        this.getChildren().add(ammoLabel);
    }


    public void setMessage(String text) {
        msg.setText(text);
    }

    public void displaySaveButton() {
        ImageView saveBtn = new ImageView(new Image("file:././assets/images/save.png"));
        saveBtn.setTranslateY(gameScene.getHeight() - 50);
        saveBtn.setTranslateX(10);
        this.getChildren().add(saveBtn);

        saveBtn.setOnMouseClicked(event -> {
            gameScene.saveGame();
        });
    }

    public void displayLives(){
        livesPane = new GridPane();
        livesPane.setPadding(new Insets(10,10,10,10));
        livesPane.setHgap(10);
        livesPane.setVgap(10);
       for (int i = 0; i < gameScene.getLives(); i++) {
           ImageView lives = new ImageView(new Image("file:././assets/images/heart.png"));
           livesPane.add(lives, i, 0);
       }
       this.getChildren().add(livesPane);
    }

    public void addDoors() {
        topDoor = new Rectangle(500, 0, 200, 30);
        bottomDoor = new Rectangle(500, 720, 200, 30);
        rightDoor = new Rectangle(1170, 275, 30, 200);
        leftDoor = new Rectangle(0, 275, 30, 200);

        this.getChildren().addAll(topDoor, bottomDoor, rightDoor, leftDoor);
    }

    public void activateCurse() {
        if (!isCurseActive) {
            for (int i = 0; i < gameScene.getTalkers().size(); i++) {
                if (this.getChildren().contains(gameScene.getTalkers().get(i))) {
                    gameScene.getTalkers().get(i).curseChooser();
                    isCurseActive = true;
                }
            }
        }
    }

    public void removeDoors() {
        if ( (this.posX - 1) < 0) {
            this.getChildren().remove(leftDoor);
        }

        if ( (this.posX + 1) > (gameScene.getRows() - 1)) {
            this.getChildren().remove(rightDoor);
        }

        if ( (this.posY - 1) < 0) {
            this.getChildren().remove(topDoor);
        }

        if ((this.posY + 1) > (gameScene.getColumns() - 1)) {
            this.getChildren().remove(bottomDoor);
        }
    }

    public Rectangle getBottomDoor() {
        return bottomDoor;
    }

    public Rectangle getLeftDoor() {
        return leftDoor;
    }

    public Rectangle getRightDoor() {
        return rightDoor;
    }

    public Rectangle getTopDoor() {
        return topDoor;
    }
}
