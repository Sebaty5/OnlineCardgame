package de.voidstack_overload.cardgame.application;
import de.voidstack_overload.cardgame.controller.StartupScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class GameManager extends Application {

    public static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Durak");
        stage.setResizable(true);
        stage.setMinWidth(800);
        stage.setMinHeight(600);

        Group root = new Group();
        Scene scene = new Scene(root, stage.getWidth(), stage.getHeight(), Color.RED);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("StartupScreen.fxml"));
        Pane mainPane = fxmlLoader.load();
        StartupScreenController controller = fxmlLoader.getController();




        stage.setScene(new Scene(mainPane, stage.getWidth(), stage.getHeight()));

        GameManager.stage = stage;
        GameManager.stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
