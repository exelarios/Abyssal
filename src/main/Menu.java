package main;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

import java.awt.event.MouseEvent;

public class Menu extends BorderPane {

    private Pane centerPane;
    private Scene menuScene;
    private int width;
    private int height;

    public Menu(Stage currentStage, int width, int height) {
        centerPane = new Pane();
        this.width = width;
        this.height = height;

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
        MenuButton button = new MenuButton("Play", -50, 300);
        centerPane.getChildren().add(button);
        button.setOnMouseClicked(event -> {
            Game game = new Game(currentStage, width, height);
        });
    }

    public void loadButton(Stage currentStage) {
        MenuButton button = new MenuButton("Load", -50, 360);
        centerPane.getChildren().add(button);
        button.setOnMouseClicked(event -> {
            System.out.println("load");
        });
    }

    public void saveButton(Stage currentStage) {
        MenuButton button = new MenuButton("Save", -50, 420);
        centerPane.getChildren().add(button);
        button.setOnMouseClicked(event -> {
            System.out.println("save");
        });
    }
}
