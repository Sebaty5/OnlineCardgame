package de.voidstack_overload.cardgame.controller;

import de.voidstack_overload.cardgame.application.GameManager;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StartupScreenController {

    double progressbarValue;
    int texturesLoaded;

    @FXML
    private void continueButtonClicked(ActionEvent event) throws Exception {
        Stage stage = GameManager.stage;
        texturesLoaded = 0;
        progressbarValue = 0.0;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("LoadingScreen.fxml"));
        Pane loadingPane = fxmlLoader.load();
        stage.setScene(new Scene(loadingPane, stage.getWidth(), stage.getHeight()));


        ProgressBar progressBar = (ProgressBar) stage.getScene().lookup("#LoadingBar");
        progressBar.setProgress(0.0);

        int textureAmount = 5;


        PauseTransition pauseTransition = new PauseTransition(Duration.millis(1000));
        pauseTransition.setOnFinished(
                e->
                {
                    if(texturesLoaded < textureAmount) {
                        //loading texture
                        texturesLoaded++;
                        progressbarValue = (1.0 / textureAmount) * texturesLoaded;
                        progressBar.setProgress(progressbarValue);
                        pauseTransition.playFromStart();
                    }
                }
        );
        pauseTransition.play();
    }



}
