package de.voidstack_overload.cardgame.application;

import de.voidstack_overload.cardgame.SceneFXML;
import de.voidstack_overload.cardgame.SceneManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GameClient extends Application {
    private static SceneManager sceneManager;

    public static SceneManager getSceneManager() {
        return sceneManager;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        sceneManager = new SceneManager(primaryStage);
        primaryStage.setOnCloseRequest((WindowEvent evt) -> {
            Platform.exit();
            System.exit(0);
        });

        primaryStage.setTitle("Durak");
        primaryStage.setResizable(false);

        sceneManager.switchScene(SceneFXML.STARTUP);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
