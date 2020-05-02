package main;

import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Trap extends Rectangle {

    public Trap() {

        Random rand = new Random();

        this.setHeight(100);
        this.setWidth(100);
        this.getStyleClass().add("trap");
        this.setX(rand.nextInt(1100 - 100 + 1) + 100);
        this.setY(rand.nextInt(650 - 100 + 1) + 100);
        this.setRotate(rand.nextInt(361));
    }
}
