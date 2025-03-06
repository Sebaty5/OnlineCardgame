package de.voidstack_overload.cardgame.controller_OLD;

import de.voidstack_overload.cardgame.utility.FXMLNavigator;
import de.voidstack_overload.cardgame.application.GameClient;
import de.voidstack_overload.cardgame.PaneResizer;
import de.voidstack_overload.cardgame.model.Cards;
import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.util.Duration;


public class LoadingScreenController implements ControllerInterface {

    float progressBarValue;
    int imageNumber;

    public LoadingScreenController() {
        progressBarValue = 0.0f;
        imageNumber = 0;
    }

    @Override
    public void init() {
        Stage stage = GameClient.stage;
        Scene scene = stage.getScene();

        ProgressBar progressBar = (ProgressBar) scene.lookup("#LoadingBar");
        progressBar.setProgress(progressBarValue);

        PauseTransition pause = new PauseTransition(Duration.millis(1.0));
        pause.setOnFinished(event -> {
            if(imageNumber < Cards.getImageBufferSize()) {
                Cards.getImage(imageNumber);
                progressBarValue = 1.0f * imageNumber / Cards.getImageBufferSize();
                progressBar.setProgress(progressBarValue);
                imageNumber++;
                pause.playFromStart();
            } else {
                FXMLNavigator.loadFxml(FXMLNavigator.HOME);
            }
        });
        pause.play();
        PaneResizer.resizePane(scene.getHeight(), true);
    }



}