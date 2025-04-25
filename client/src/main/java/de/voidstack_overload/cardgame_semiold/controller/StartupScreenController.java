package de.voidstack_overload.cardgame_semiold.controller;

import de.voidstack_overload.cardgame_semiold.SceneFXML;
import javafx.fxml.FXML;
import java.io.IOException;

public class StartupScreenController extends BaseController {

    @FXML
    private void continueButtonClicked() throws IOException {
        sceneManager.switchScene(SceneFXML.LOADING);
    }
}

