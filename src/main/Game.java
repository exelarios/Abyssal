package main;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

/*
    TODO: Final implementation.
        - show map.

    TODO: Save and Load Feature
        - Saves
            - rows, Columns
            - posX, posY
            - player's Name
            - speed
            - ammo
            - lives
            - boss lives
 */

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
    private int ammo;
    private int speed;
    private int amountOfPowerUps;

    private Random rand = new Random();

    private Cave[][] map;

    private ArrayList<Coordinate> pos = new ArrayList<>();
    private ArrayList<Trap> pits = new ArrayList<>();
    private ArrayList<Talker> talkers = new ArrayList<>();
    private ArrayList<PowerUp> powerUps = new ArrayList<>();

    private Boss solomon;
    private int bossLives = 3;

    public static boolean collidedDebounce;
    public static boolean touchedTrap;
    public static boolean walkerActive;
    public static boolean playerWon;

    public static String direction;

    public Game(Stage currentStage, int width, int height) {
        super(new Pane(), width, height);
        this.setRoot(gamePane);

        gamePane.setStyle("-fx-background-color: black");

        this.width = width;
        this.height = height;

        this.lives = 3;
        this.ammo = 0;
        this.speed = 10;

        currentStage.setScene(this);
        this.getStylesheets().add("file:././assets/css/game.css");

        getGameInfo();
    }

    public Game(Stage currentStage, int width, int height, String name, int rows, int columns, int lives, int ammo, int speed, int bossLives) {
        super(new Pane(), width, height);
        this.setRoot(gamePane);
        gamePane.setStyle("-fx-background-color: black");
        currentStage.setScene(this);
        this.getStylesheets().add("file:././assets/css/game.css");

        this.width = width;
        this.height = height;

        this.name = name;
        this.rows = rows;
        this.columns = columns;
        this.lives = lives;
        this.ammo = ammo;
        this.speed = speed;
        this.bossLives = bossLives;

        initializeGame();
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

    public int getLives() {
        return this.lives;
    }

    public Player getPlayer() {
        return player;
    }

    public void setAmmo(int value) {
        this.ammo = value;
        getCurrentCave().getChildren().remove(getCurrentCave().getDisplayAmmo());
        getCurrentCave().displayAmmo();
    }

    public int getAmmo() {
        return this.ammo;
    }

    public int getBossLives() {
        return this.bossLives;
    }

    public void setBossLives(int lives) {
        this.bossLives = lives;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setLives(int lives) {
        if (lives < 1) {
            gameOver();
        } else {
            this.lives = lives;
            updateLivesUI();
        }
    }

    public void updateLivesUI() {
        this.getCurrentCave().getChildren().remove(this.getCurrentCave().getLivesPane());
        this.getCurrentCave().displayLives();
    }

    public Cave getCurrentCave() {
        return this.map[this.posX][this.posY];
    }

    public Cave[][] getCave() {
        return this.map;
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

    public ArrayList<Trap> getPits() {
        return this.pits;
    }

    public ArrayList<Talker> getTalkers() {
        return this.talkers;
    }

    public ArrayList<PowerUp> getPowerUps() {
        return this.powerUps;
    }

    public Boss getBoss() {
        return this.solomon;
    }

    public void setBoss(Boss solomon) {
        this.solomon = solomon;
    }

    private void getGameInfo() {

        GameForm gameInfo = new GameForm( 260, 200);

        gamePane.getChildren().add(gameInfo);

        gameInfo.getButton().setOnMouseClicked(event -> {
            initializeGame(gameInfo);
        });

        this.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if ((gameInfo.getName() != null) && (gameInfo.getRows() != null) && (gameInfo.getColumn() != null)) {
                    initializeGame(gameInfo);
                }
            }
        });
    }

    public void gameOver() {
        setRoot(new GameOver(this, "Game Over"));
    }

    private void initializeGame(GameForm gameInfo) {
        this.name = gameInfo.getName();
        this.rows = Integer.parseInt(gameInfo.getRows());
        this.columns = Integer.parseInt(gameInfo.getColumn());

        gamePane.requestFocus(); // remove text input focus.

        if ((this.rows >= 4) && (this.columns >= 4)) {
            initializeMap();
            enterRoom(575, 350);
            addTalkers();
        }
    }

    private void initializeGame() {
        if ((this.rows >= 4) && (this.columns >= 4)) {
            initializeMap();
            enterRoom(575, 350);
            addTalkers();
        }
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
        addPowerUps();
        addSolomon();

    }

    private void addPits() {

        int amountOfPits =  rand.nextInt((this.rows * this.columns) / 3) + 1;
        System.out.println("Total pits: " + amountOfPits);

        for (int i = 0; i < amountOfPits; i++) {
            this.pits.add(new Trap());

            int getRandomPosX = rand.nextInt(this.rows);
            int getRandomPosY = rand.nextInt(this.columns);

            // Make sure the traps are not spawned at the same place or the player's spawn.
            while ((isBlacklistCoordinate(getRandomPosX, getRandomPosY))
                    || (getRandomPosX == this.posX) && (getRandomPosY == this.posY) ) {
                getRandomPosX = rand.nextInt(this.rows);
                getRandomPosY = rand.nextInt(this.columns);
            }

            map[getRandomPosX][getRandomPosY].getChildren().add(this.pits.get(i));
            pos.add(new Coordinate(getRandomPosX, getRandomPosY));
        }
    }

    private void addTalkers() {
        int amountofTalkers = rand.nextInt((this.rows * this.columns) / 3) + 1;
        System.out.println("Total Talkers: " + amountofTalkers);
       for (int i = 0; i < amountofTalkers; i++) {
           this.talkers.add(new Talker(this));

           int getRandomPosX = rand.nextInt(this.rows);
           int getRandomPosY = rand.nextInt(this.columns);

           while ((isBlacklistCoordinate(getRandomPosX, getRandomPosY))
                   || (getRandomPosX == this.posX) && (getRandomPosY == this.posY) ) {
               getRandomPosX = rand.nextInt(this.rows);
               getRandomPosY = rand.nextInt(this.columns);
           }

           map[getRandomPosX][getRandomPosY].getChildren().add(this.talkers.get(i));
           pos.add(new Coordinate(getRandomPosX, getRandomPosY));
       }
    }

    public boolean isBlacklistCoordinate(int posX, int posY) {
        for (int i = 0; i < pos.size(); i++) {
            if ( (pos.get(i).getPosX() == posX) && (pos.get(i).getPosY() == posY)) {
                return true;
            }
        }
        return false;
    }

    private void addPowerUps() {
        amountOfPowerUps = rand.nextInt( ((this.rows * this.columns) / 4) ) + 1;
        System.out.println("Total PowerUs: " + amountOfPowerUps);

        for(int i = 0; i < amountOfPowerUps; i++) {
            this.powerUps.add(new PowerUp(this));

            int getRandomPosX = rand.nextInt(this.rows);
            int getRandomPosY = rand.nextInt(this.columns);

            map[getRandomPosX][getRandomPosY].getChildren().add(this.powerUps.get(i));
            pos.add(new Coordinate(getRandomPosX, getRandomPosY));
        }

    }

    private void addSolomon() {
        int getRandomPosX = rand.nextInt(this.rows);
        int getRandomPosY = rand.nextInt(this.columns);

        while ((isBlacklistCoordinate(getRandomPosX, getRandomPosY))
                || (getRandomPosX == this.posX) && (getRandomPosY == this.posY) ) {
            getRandomPosX = rand.nextInt(this.rows);
            getRandomPosY = rand.nextInt(this.columns);
        }

        generateBoss(getRandomPosX, getRandomPosY);
        this.pos.add(new Coordinate(getRandomPosX, getRandomPosY));
    }

    public void generateBoss(int posX, int posY) {
        System.out.println("Boss Location: " + posX + ", " + posY);
        solomon = new Boss(this, posX, posY);
        this.map[posX][posY].getChildren().add(solomon);
    }

    public void generateMorePowerUps() {
        if (PowerUp.powerUpCounter >= amountOfPowerUps) {
            PowerUp.powerUpCounter = 0;
            addPowerUps();
        }
    }

    private void generatePlayer(int spawnX, int spawnY) {
        player = new Player(this, this.speed, "#e67e22", 50, this.width, this.height,
                this.map, this.posX, this.posY, spawnX, spawnY);

        map[this.posX][this.posY].getChildren().add(player);
        map[this.posX][this.posY].activateCurse();
    }

    public void enterRoom(int spawnX, int spawnY) {
        this.setRoot(map[this.posX][this.posY]);
        generatePlayer(spawnX, spawnY);
        this.updateLivesUI();
        this.setAmmo(this.getAmmo());
        Game.collidedDebounce = false;
        Game.touchedTrap = false;

    }

    private void generatehelperMap() {

    }

    public void finishGame() {
        setRoot(new GameOver(this, "You woke up from a dream."));
    }


}
