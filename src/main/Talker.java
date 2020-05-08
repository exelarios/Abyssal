package main;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Talker extends Rectangle {

    private Random rand = new Random();
    private Game mainScene;
    private String curse;

    public Talker(Game mainScene) {

        this.mainScene = mainScene;
        int size = 50;
        this.setHeight(size);
        this.setStyle("-fx-fill: #9b59b6");
        this.setWidth(size);
        this.setX(rand.nextInt(1100 - 100 + 1) + 100);
        this.setY(rand.nextInt(650 - 100 + 1) + 100);



        String[] curses = new String[]{
                "HeartSteal", "SpeedReducer", "Teleporter"
        };
        this.curse = curses[rand.nextInt(curses.length)];
        //this.curse = curses[2];
    }

    public void curseChooser() {
        switch (this.curse) {
            case "HeartSteal":
                mainScene.getCurrentCave().setMessage("A spell has been casted, one of your life has been taken by the deweller.");
                mainScene.setLives(mainScene.getLives() - 1);
                break;
            case "SpeedReducer":
                mainScene.getCurrentCave().setMessage("A spell has been casted, you noitced your movement is slowing down.");
                mainScene.getPlayer().setSpeed(mainScene.getPlayer().getSpeed() / 2);
                mainScene.setSpeed(mainScene.getSpeed() / 2);
                break;
            case "Teleporter":
                mainScene.getCurrentCave().setMessage("A spell has been casted, your body seems to be fading into a different location.");
                //mainScene.getPlayer().setSpeed(0);
                Task<Void> sleeper = new Task<Void>() {

                    @Override
                    protected Void call() throws Exception {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException ignored) {

                        }
                        return null;
                    }
                };

                sleeper.setOnSucceeded( event -> {
                    mainScene.setPosX(rand.nextInt(mainScene.getRows()));
                    mainScene.setPosY(rand.nextInt(mainScene.getColumns()));
                    mainScene.getPlayer().setStyle("-fx-fill: rgba(0,0,0,0);");
                    mainScene.enterRoom(300, 400);
                });

                new Thread(sleeper).start();
                break;
        }
    }
}
