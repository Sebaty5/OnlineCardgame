package de.voidstack_overload.cardgame.application;

import de.voidstack_overload.cardgame.SceneFXML;
import de.voidstack_overload.cardgame.SceneManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.util.List;

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
        List<Image> icons = List.of(
                new Image(GameClient.class.getResourceAsStream("/textures/icons/glow_club.png")),
                new Image(GameClient.class.getResourceAsStream("/textures/icons/glow_diamond.png")),
                new Image(GameClient.class.getResourceAsStream("/textures/icons/glow_heart.png")),
                new Image(GameClient.class.getResourceAsStream("/textures/icons/glow_spade.png"))
        );

        primaryStage.getIcons().setAll(icons.get(0));

        final int[] idx = {1};
        Timeline cycler = new Timeline(
                new KeyFrame(Duration.millis(250), e -> {
                    primaryStage.getIcons().set(0, icons.get(idx[0]));
                    idx[0] = (idx[0] + 1) % icons.size();
                })
        );
        cycler.setCycleCount(Animation.INDEFINITE);
        cycler.play();

        sceneManager.switchScene(SceneFXML.STARTUP);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
