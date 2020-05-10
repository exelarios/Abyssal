package entity;

import entity.Player;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import view.Game;

public class Bullet extends Rectangle {

    private Pane currentPane;
    private Game mainScene;
    private Player player;
    private Point2D velocity = new Point2D(0,0);
    private boolean fired;

    public Bullet(Player player, Game mainScene, Pane currentPane) {
        this.currentPane = currentPane;
        this.player = player;

        this.mainScene = mainScene;

        this.fired = false;

        this.setWidth(10);
        this.setHeight(10);
        this.setStyle("-fx-fill: white;");

        this.setX(player.getX() + (player.getWidth() - this.getWidth())/ 2 );
        this.setY(player.getY() + (player.getHeight() - this.getHeight()) / 2);

        projectile();

    }

    public Point2D getVelocity() {
        return velocity;
    }

    public void update() {
        this.setX(this.getX() + velocity.getX());
        this.setY(this.getY() + velocity.getY());
        checkCollision();

    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public void projectile() {
        switch (Game.direction) {
            case "North":
                this.setVelocity(new Point2D(0, -10));
                break;
            case "South":
                this.setVelocity(new Point2D(0, 10));
                break;
            case "East":
                this.setVelocity(new Point2D(10, 0));
                break;
            case "West":
                this.setVelocity(new Point2D(-10, 0));
            default:
                break;
        }
    }

    public void checkCollision() {
        if ( (this.getBoundsInParent().intersects(mainScene.getBoss().getBoundsInParent())) && !Game.playerWon && !fired ) {
            if ( mainScene.getBossLives() > 1) {
                mainScene.setBossLives( mainScene.getBossLives() - 1);
                mainScene.getBoss().setWidth(mainScene.getBoss().getWidth() - 5);
                mainScene.getBoss().setHeight(mainScene.getBoss().getHeight() - 5);
                this.fired = true;
            } else {
                Game.playerWon = true;
                mainScene.getCurrentCave().getChildren().removeAll(mainScene.getBoss(), this);
                mainScene.finishGame();
            }
        }
    }
}
