package entity;

import javafx.scene.shape.Rectangle;
import view.Game;

import java.util.Random;

public class PowerUp extends Rectangle {

    private Game mainScene;
    private Random rand = new Random();
    private String powerUp;
    public static int powerUpCounter = 0;

    public PowerUp(Game mainScene) {

        this.mainScene = mainScene;
        int size = 20;
        this.setHeight(size);
        this.setWidth(size);
        this.setStyle("-fx-fill: #2ecc71");
        this.setX(rand.nextInt(1100 - 100 + 1) + 100);
        this.setY(rand.nextInt(650 - 100 + 1) + 100);


        String[] powerUps = new String[] {
                "Heart Boost", "Speed Boost", "Ammo"
        };

        this.powerUp = powerUps[rand.nextInt(powerUps.length)];
    }

    public void powerUpChooser() {
        switch (this.powerUp) {
            case "Heart Boost":
                System.out.println("health boost");
                mainScene.getCurrentCave().setMessage("You found a potion, you drank it. It gave you an extra live.");
                mainScene.setLives(mainScene.getLives() + 1);
                break;
            case "Speed Boost":
                mainScene.getCurrentCave().setMessage("You found a potion, you drank it. You felt more energetic.");
                mainScene.getPlayer().setSpeed(mainScene.getPlayer().getSpeed() + 5);
                mainScene.setSpeed(mainScene.getSpeed() + 5);
                System.out.println("speed boost");
                break;
            case "Ammo":
                mainScene.getCurrentCave().setMessage("You found ammo.");
                mainScene.setAmmo(mainScene.getAmmo() + 1);
                System.out.println("amm");
                break;
        }
        powerUpCounter++;
        System.out.println(PowerUp.powerUpCounter);
        mainScene.getCurrentCave().getChildren().remove(this);
        mainScene.getCurrentCave().setIsPowerIsActive(false);
    }
}
