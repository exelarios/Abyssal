package main;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Menu extends BorderPane {

    private Pane centerPane;
    private Scene menuScene;
    private int width;
    private int height;
    private Stage currentStage;

    public Menu(Stage currentStage, int width, int height) {
        centerPane = new Pane();
        this.width = width;
        this.height = height;

        this.currentStage = currentStage;

        menuScene = new Scene(this, width, height);
        currentStage.setScene(menuScene);
        currentStage.setTitle("Abyssal Plain");
        currentStage.show();
        currentStage.setResizable(false);

        this.setStyle("-fx-background-color: black");
        this.setCenter(centerPane);
        this.setPadding(new Insets(100, 100, 100, 100));
        this.getStylesheets().add("file:././assets/css/menu.css");
        setLogo(centerPane);
        playButton(currentStage);
        loadButton(currentStage);
        saveButton(currentStage);
    }

    public void setLogo(Pane pane) {
        ImageView logoView = new ImageView(new Image("file:././assets/images/logo.png"));
        centerPane.getChildren().add(logoView);
    }

    public void playButton(Stage currentStage) {
        MenuButton button = new MenuButton("New Game", -50, 300);
        centerPane.getChildren().add(button);
        button.setOnMouseClicked(event -> {
            Game game = new Game(currentStage, width, height);
        });
    }

    public void loadButton(Stage currentStage) {
        MenuButton button = new MenuButton("Load", -50, 360);
        centerPane.getChildren().add(button);
        button.setOnMouseClicked(event -> {
            fetchUserDataInput();
        });
    }

    public void saveButton(Stage currentStage) {
        MenuButton button = new MenuButton("Save", -50, 420);
        centerPane.getChildren().add(button);
        button.setOnMouseClicked(event -> {
            System.out.println("save");
        });
    }

    private void fetchUserDataInput() {
        centerPane.getChildren().clear();
        Text title = new Text("Please enter the name of the session: ");
        title.setStyle("-fx-fill: white; -fx-font-size: 30px");
        title.setTranslateX(250);
        title.setTranslateY(180);

        TextField getName = new TextField();
        getName.setStyle("-fx-background-radius: 0; -fx-font-size: 30px");
        getName.setPrefWidth(500);
        getName.setTranslateY(200);
        getName.setTranslateX(250);

        MenuButton submit = new MenuButton("Submit", 565, 270);
        centerPane.getChildren().addAll(getName, title, submit);

        submit.setOnMouseClicked( event -> {
            fetchUser("data/" + getName.getText() + ".csv");
        });
    }

    private void fetchUser(String fetchUser) {
        File userData = new File(fetchUser);

        try {
            Scanner reader = new Scanner(userData);

            String[] tokens = reader.nextLine().split(",");

            String name = tokens[0];
            int rows = Integer.parseInt(tokens[1]);
            int columns = Integer.parseInt(tokens[2]);
            int lives = Integer.parseInt(tokens[3]);
            int ammo = Integer.parseInt(tokens[4]);
            int speed = Integer.parseInt(tokens[5]);
            int bossLives = Integer.parseInt(tokens[6]);

            Game game = new Game(this.currentStage, this.width, this.height, name, rows, columns, lives, ammo, speed, bossLives);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
