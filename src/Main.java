import javafx.application.Application;
import javafx.stage.Stage;
import view.Menu;

public class Main extends Application {

    @Override
    public void start(Stage currentStage) throws Exception {

        final int width = 1200;
        final int height = 750;

        try {
            Menu menu = new Menu(currentStage, width, height);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

}
