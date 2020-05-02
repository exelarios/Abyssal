package main;

import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;

public class Player extends Rectangle {

    private int speed;
    private int screenWidth;
    private int screenHeight;
    private Game gameScene;
    private Cave[][] map;
    private int posX;
    private int posY;

    public Player(Game gameScene, int speed, String color, int size,
                  int screenWidth, int screenHeight, Cave[][] cave, int posX, int posY, int spawnX, int spawnY) {

        this.gameScene = gameScene;

        this.speed = speed;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.map = cave;
        this.posX = posX;
        this.posY = posY;

        this.setX(spawnX);
        this.setY(spawnY);

        this.setStyle("-fx-fill: " + color);
        this.setWidth(size);
        this.setHeight(size);
        movements();
    }

    public void movements() {
        gameScene.setOnKeyPressed(event -> {
            checkDoorCollision();
            switch(event.getCode()) {
                case W:
                    if (this.getY() > 0) {
                        this.setY(this.getY() - speed);
                    }
                    break;
                case A:
                    if (this.getX() > 0) {
                        this.setX(this.getX() - this.speed);
                    }
                    break;
                case S:
                    if (this.getY() <= this.screenHeight - (this.getHeight() + this.speed)) {
                        this.setY(this.getY() + speed);
                    }
                    break;
                case D:
                    if (this.getX() <= this.screenWidth - (this.getWidth() + this.speed)) {
                        this.setX(this.getX() + this.speed);
                    }
                    break;
                default:
                    break;
            }

        });
    }

    private boolean isCollided(Bounds other) {
        if (this.getBoundsInParent().intersects(other)) {
            return true;
        }
        return false;
    }

    private void checkDoorCollision() {
        if (isCollided(map[this.posX][this.posY].getBottomDoor().getBoundsInParent())) {
            if ( ((gameScene.getPosY() + 1) < gameScene.getColumns()) && !Game.collidedDebounce ) {
                Game.collidedDebounce = true;
                gameScene.setPosY(gameScene.getPosY() + 1);
                gameScene.printPlayerPosition();
                map[this.posX][this.posY].getChildren().remove(this);
                gameScene.enterRoom(575,50);
            }
        }

        if (isCollided(map[this.posX][this.posY].getTopDoor().getBoundsInParent())) {
            if ( ((gameScene.getPosY() - 1)  >= 0) && !Game.collidedDebounce)  {
                Game.collidedDebounce = true;
                gameScene.setPosY(gameScene.getPosY() - 1);
                gameScene.printPlayerPosition();
                map[this.posX][this.posY].getChildren().remove(this);
                gameScene.enterRoom(575,650);
            }
        }

        if (isCollided(map[this.posX][this.posY].getLeftDoor().getBoundsInParent())) {
            if ( ((gameScene.getPosX() - 1)  >= 0) && !Game.collidedDebounce)  {
                Game.collidedDebounce = true;
                gameScene.setPosX(gameScene.getPosX() - 1);
                gameScene.printPlayerPosition();
                map[this.posX][this.posY].getChildren().remove(this);
                gameScene.enterRoom(1100,350);
            }
        }

        if (isCollided(map[this.posX][this.posY].getRightDoor().getBoundsInParent())) {
            if ( ((gameScene.getPosX() + 1) < gameScene.getRows()) && !Game.collidedDebounce)  {
                Game.collidedDebounce = true;
                gameScene.setPosX(gameScene.getPosX() + 1);
                gameScene.printPlayerPosition();
                map[this.posX][this.posY].getChildren().remove(this);
                gameScene.enterRoom(50,350);
            }
        }
    }
}
