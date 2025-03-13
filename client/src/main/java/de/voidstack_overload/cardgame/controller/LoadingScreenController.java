package de.voidstack_overload.cardgame.controller;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;
import java.io.IOException;

public class LoadingScreenController extends BaseController {

    @FXML
    private ProgressBar loadingBar;

    private double progressbarValue = 0.0;
    private int texturesLoaded = 0;

    @FXML
    private void initialize() {
        loadingBar.setProgress(0.0);
        int textureAmount = 5;

        PauseTransition pauseTransition = new PauseTransition(Duration.millis(1000));
        pauseTransition.setOnFinished(
                e -> {
                    if (texturesLoaded < textureAmount) {
                        //loading texture
                        texturesLoaded++;
                        progressbarValue = (1.0 / textureAmount) * texturesLoaded;
                        loadingBar.setProgress(progressbarValue);
                        pauseTransition.playFromStart();
                    } else {
                        try {
                            sceneManager.switchScene("HomeScreen.fxml");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
        );
        pauseTransition.play();
    }
}
