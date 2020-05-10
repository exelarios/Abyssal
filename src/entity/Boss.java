package entity;


import javafx.scene.shape.Rectangle;
import view.Game;

import java.util.Random;


public class Boss extends Rectangle {

    private int size;
    private Random rand = new Random();
    private Game mainScene;
    private int posX;
    private int posY;

    public Boss(Game mainScene, int posX, int posY) {

        this.mainScene = mainScene;
        this.posX = posX;
        this.posY = posY;
        this.size = 70;
        this.setWidth(this.size);
        this.setHeight(this.size);
        this.setX(rand.nextInt(1100 - 100 + 1) + 100);
        this.setY(rand.nextInt(650 - 100 + 1) + 100);
        this.setStyle("-fx-fill: #34495e");

    }

    public int getPosY() {
        return posY;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void teleport() {
        if ((mainScene.getPosX() != this.posX) && (mainScene.getPosY() != this.posY)) {
            int randomPosX = rand.nextInt(mainScene.getRows());
            int randomPosY = rand.nextInt(mainScene.getColumns());

            mainScene.generateBoss(randomPosX, randomPosY);
            mainScene.getCave()[this.posX][this.posY].getChildren().remove(this);
        }
    }


}
