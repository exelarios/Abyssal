package entity;

import javafx.animation.AnimationTimer;
import javafx.geometry.Bounds;
import javafx.scene.shape.Rectangle;
import view.Cave;
import view.Game;

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

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void movements() {
        gameScene.setOnKeyPressed(event -> {
            checkDoorCollision();
            checkTrapCollision();
            checkPowerUpCollision();
            gameScene.generateMorePowerUps();

            switch(event.getCode()) {
                case W:
                    if (this.getY() > 0) {
                        this.setY(this.getY() - speed);
                        Game.direction = "North";
                    }
                    break;
                case A:
                    if (this.getX() > 0) {
                        this.setX(this.getX() - this.speed);
                        Game.direction = "West";
                    }
                    break;
                case S:
                    if (this.getY() <= this.screenHeight - (this.getHeight() + this.speed)) {
                        this.setY(this.getY() + speed);
                        Game.direction = "South";
                    }
                    break;
                case D:
                    if (this.getX() <= this.screenWidth - (this.getWidth() + this.speed)) {
                        this.setX(this.getX() + this.speed);
                        Game.direction = "East";
                    }
                    break;
                case SPACE:
                    if (gameScene.getAmmo() > 0) {
                        gameScene.getBoss().teleport();
                        Bullet bullet = new Bullet(this, gameScene, map[this.posX][this.posY]);
                        map[this.posX][this.posY].getChildren().add(bullet);
                        bullet.projectile();
                        gameScene.setAmmo(gameScene.getAmmo() - 1);

                        AnimationTimer timer = new AnimationTimer() {
                            @Override
                            public void handle(long now) {
                                bullet.update();
                            }
                        };
                        timer.start();
                    }
                    break;
                case B:
                    gameScene.setPosX(gameScene.getBoss().getPosX());
                    gameScene.setPosY(gameScene.getBoss().getPosY());

                    map[this.posX][this.posY].getChildren().remove(this);
                    gameScene.enterRoom(575,50);

                    gameScene.setAmmo(5);

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
                //gameScene.printPlayerPosition();
                map[this.posX][this.posY].getChildren().remove(this);
                gameScene.enterRoom(575,50);
            }
        }

        if (isCollided(map[this.posX][this.posY].getTopDoor().getBoundsInParent())) {
            if ( ((gameScene.getPosY() - 1)  >= 0) && !Game.collidedDebounce)  {
                Game.collidedDebounce = true;
                gameScene.setPosY(gameScene.getPosY() - 1);
                //gameScene.printPlayerPosition();
                map[this.posX][this.posY].getChildren().remove(this);
                gameScene.enterRoom(575,650);
            }
        }

        if (isCollided(map[this.posX][this.posY].getLeftDoor().getBoundsInParent())) {
            if ( ((gameScene.getPosX() - 1)  >= 0) && !Game.collidedDebounce)  {
                Game.collidedDebounce = true;
                gameScene.setPosX(gameScene.getPosX() - 1);
                //gameScene.printPlayerPosition();
                map[this.posX][this.posY].getChildren().remove(this);
                gameScene.enterRoom(1100,350);
            }
        }

        if (isCollided(map[this.posX][this.posY].getRightDoor().getBoundsInParent())) {
            if ( ((gameScene.getPosX() + 1) < gameScene.getRows()) && !Game.collidedDebounce)  {
                Game.collidedDebounce = true;
                gameScene.setPosX(gameScene.getPosX() + 1);
                //gameScene.printPlayerPosition();
                map[this.posX][this.posY].getChildren().remove(this);
                gameScene.enterRoom(50,350);
            }
        }
    }

    private void checkTrapCollision() {
        for (int pit = 0; pit < gameScene.getPits().size(); pit++) {
            if (map[this.posX][this.posY].getChildren().contains(gameScene.getPits().get(pit))) {
                map[this.posX][this.posY].setMessage("You look around and you notice that the ground looks a bit odd.");
                if (isCollided(gameScene.getPits().get(pit).getBoundsInParent()) && !gameScene.getCurrentCave().getTouchedTrap()) {
                    gameScene.getCurrentCave().setTouchedTrap(true);
                    gameScene.setLives(gameScene.getLives() - 3);
                }
            }
        }
    }

    private void checkPowerUpCollision() {
        for (int powerUp = 0; powerUp < gameScene.getPowerUps().size(); powerUp++) {
            if (map[this.posX][this.posY].getChildren().contains(gameScene.getPowerUps().get(powerUp))) {
                if ( (isCollided(gameScene.getPowerUps().get(powerUp).getBoundsInParent())) && !gameScene.getCurrentCave().getIsPowerUpActive() ) {
                    gameScene.getCurrentCave().setIsPowerIsActive(true);
                    gameScene.getPowerUps().get(powerUp).powerUpChooser();
                }
            }
        }
    }
}
