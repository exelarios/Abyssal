package main;

import com.sun.xml.internal.bind.v2.runtime.Coordinator;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class Game extends Scene {

    private Pane gamePane = new Pane();

    private int width;
    private int height;

    private int rows;
    private int columns;

    private int posX;
    private int posY;

    private Player player;
    private String name;
    private int lives;
    private Random rand = new Random();

    private Cave[][] map;

    private ArrayList<Coordinate> pos = new ArrayList<>();
    private ArrayList<Trap> pits = new ArrayList<>();

    public static boolean collidedDebounce;

    public Game(Stage currentStage, int width, int height) {
        super(new Pane(), width, height);
        this.setRoot(gamePane);

        gamePane.setStyle("-fx-background-color: black");

        this.width = width;
        this.height = height;

        currentStage.setScene(this);
        this.getStylesheets().add("file:././assets/css/game.css");

        getGameInfo();
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public String getName() {
        return this.name;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void printPlayerPosition() {
        System.out.println( this.getPosX() + ", " + this.getPosY());
    }

    private void getGameInfo() {

        GameForm gameInfo = new GameForm(260, 200);

        gamePane.getChildren().add(gameInfo);

        gameInfo.getButton().setOnMouseClicked(event -> {
            this.name = gameInfo.getName();
            this.rows = Integer.parseInt(gameInfo.getRows());
            this.columns = Integer.parseInt(gameInfo.getColumn());

            gamePane.requestFocus(); // remove text input focus.

            initializeMap();
            enterRoom(575, 350);
        });
    }

    private void initializeMap() {

        map = new Cave[this.rows][this.columns];

        this.posX = rand.nextInt(this.rows);
        this.posY = rand.nextInt(this.columns);

        System.out.println("Player Location: " + this.posX + ", " + this.posY);
        System.out.println("Map Length: " + map.length + ", " + map[0].length);

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                map[i][j] = new Cave(this, this.player, i, j);
            }
        }

        addPits();

    }

    private void addPits() {

        int amountOfPits =  rand.nextInt((this.rows * this.columns) / 3) + 1;
        System.out.println("Total of pits: " + amountOfPits);

        for (int i = 0; i < amountOfPits; i++) {
            this.pits.add(new Trap());

            int getRandomPosX = rand.nextInt(this.rows);
            int getRandomPosY = rand.nextInt(this.columns);

            System.out.println(isBlacklistCoordinate(getRandomPosX, getRandomPosY));

            if (isBlacklistCoordinate(getRandomPosX, getRandomPosY)) {
                while (isBlacklistCoordinate(getRandomPosX, getRandomPosY)) {
                    getRandomPosX = rand.nextInt(this.rows);
                    getRandomPosY = rand.nextInt(this.columns);
                }

                map[getRandomPosX][getRandomPosY].getChildren().add(this.pits.get(i));
                pos.add(new Coordinate(getRandomPosX, getRandomPosY));
            } else {
                map[getRandomPosX][getRandomPosY].getChildren().add(this.pits.get(i));
                pos.add(new Coordinate(getRandomPosX, getRandomPosY));
            }
        }

        for (int i = 0; i < amountOfPits; i ++) {
            System.out.println(pos.get(i));
        }
    }

    private void generatePlayer(int spawnX, int spawnY) {
        player = new Player(this, 10, "#e67e22", 50, this.width, this.height,
                this.map, this.posX, this.posY, spawnX, spawnY);

        map[this.posX][this.posY].getChildren().add(player);
    }

    public void enterRoom(int spawnX, int spawnY) {
        this.setRoot(map[this.posX][this.posY]);
        generatePlayer(spawnX, spawnY);
        Game.collidedDebounce = false;
    }

    public boolean isBlacklistCoordinate(int posX, int posY) {
        for (int i = 0; i < pos.size(); i++) {
            if ( (pos.get(i).getPosX() == posX) && (pos.get(i).getPosY() == posY)) {
                return true;
            }
        }
        return false;
    }

    public void checkTrapCollision() {

    }

}
