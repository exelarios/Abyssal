package entity;

import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import view.Game;

import java.util.Random;

public class Walker extends Rectangle {

    private Game mainScene;
    private Point2D velocity = new Point2D(0, 0);
    private Random rand = new Random();
    private int size;
    private enum positions {
        top, right, bottom, left;
    }
    private positions startPositions;
    private double screenWidth;
    private double screenHeight;
    private int speed;

    public Walker(Game mainScene, int speed) {

        Game.walkerActive = true;
        this.mainScene = mainScene;
        this.screenWidth = mainScene.getWidth();
        this.screenHeight = mainScene.getHeight();
        this.speed = speed;


        // MIN: 50; MAX: 100;
        this.size = rand.nextInt(100 - 50 + 1) + 50;

        this.setWidth(size);
        this.setHeight(size);
        this.setStyle("-fx-fill: red");

        this.startPositions = positions.values()[rand.nextInt(positions.values().length)];

        projectile();

    }

    public Point2D getVelocity() {
        return velocity;
    }

    public void update() {
        this.setTranslateX(this.getTranslateX() + velocity.getX());
        this.setTranslateY(this.getTranslateY() + velocity.getY());
        this.setRotate(rand.nextInt(361));
    }

    public positions getPosition() {
        return this.startPositions;
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public void projectile() {
        switch (this.startPositions) {
            case top:
                this.setX(rand.nextInt( (int) ((this.screenWidth - this.getWidth()) - this.getWidth() ) + 1) + this.getWidth());
                this.setY(-size);
                this.setVelocity(new Point2D(0, this.speed));
                break;
            case right:
                this.setX(this.screenWidth + size);
                this.setY(rand.nextInt( (int) ((this.screenHeight - this.getHeight()) - this.getHeight() ) + 1) + this.getHeight());
                this.setVelocity(new Point2D(-this.speed, 0));
                break;
            case bottom:
                this.setY(this.screenHeight + size);
                this.setX(rand.nextInt( (int) ((this.screenWidth - this.getWidth()) - this.getWidth() ) + 1) + this.getWidth());
                this.setVelocity(new Point2D(0, -this.speed));
                break;
            case left:
                this.setX(-size);
                this.setY(rand.nextInt( (int) ((this.screenHeight - this.getHeight()) - this.getHeight() ) + 1) + this.getHeight());
                this.setVelocity(new Point2D(this.speed, 0));
                break;
            default:
                break;
        }
    }
}