package main;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cave extends Pane {

    private Rectangle topDoor;
    private Rectangle bottomDoor;
    private Rectangle leftDoor;
    private Rectangle rightDoor;
    private Player player;
    private int posX;
    private int posY;
    private Game gameScene;

    public Cave(Game gameScene, Player player, int posX, int posY) {
        this.gameScene = gameScene;
        this.posX = posX;
        this.posY = posY;

        Label positionLabel = new Label("(" + this.posX + ", " + this.posY + ")" );
        positionLabel.setStyle("-fx-font-size: 20px");
        positionLabel.setTextFill(Color.color(Math.random(), Math.random(), Math.random()));
        this.getChildren().add(positionLabel);

        this.setStyle("-fx-background-color: #212121");

        addDoors();
        removeDoors();
    }

    public void addDoors() {
        topDoor = new Rectangle(500, 0, 200, 30);
        bottomDoor = new Rectangle(500, 720, 200, 30);
        rightDoor = new Rectangle(1170, 275, 30, 200);
        leftDoor = new Rectangle(0, 275, 30, 200);

        this.getChildren().addAll(topDoor, bottomDoor, rightDoor, leftDoor);
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
