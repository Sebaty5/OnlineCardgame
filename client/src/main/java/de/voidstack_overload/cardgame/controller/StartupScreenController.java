package de.voidstack_overload.cardgame.controller;

import javafx.fxml.FXML;
import java.io.IOException;

public class StartupScreenController extends BaseController {

    @FXML
    private void continueButtonClicked() throws IOException {
        sceneManager.switchScene("LoadingScreen.fxml");
    }
}
