package de.voidstack_overload.cardgame.application;
import de.voidstack_overload.cardgame.controller.LoadingScreenController;
import de.voidstack_overload.cardgame.controller.StartupScreenController;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class GameManager extends Application {

    public static Stage stage;



    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Durak");
        stage.setResizable(true);
        stage.setMinWidth(800);
        stage.setMinHeight(600);



        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("StartupScreen.fxml"));
        Pane mainPane = fxmlLoader.load();

        stage.setScene(new Scene(mainPane, stage.getWidth(), stage.getHeight()));







        GameManager.stage = stage;
        GameManager.stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
