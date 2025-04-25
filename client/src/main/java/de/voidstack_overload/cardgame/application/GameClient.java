package de.voidstack_overload.cardgame.application;

import de.voidstack_overload.cardgame.SceneFXML;
import de.voidstack_overload.cardgame.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class GameClient extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneManager sceneManager = new SceneManager(primaryStage);

        primaryStage.setTitle("Durak");
        primaryStage.setResizable(false);


        sceneManager.switchScene(SceneFXML.STARTUP);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
