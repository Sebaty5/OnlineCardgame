package de.voidstack_overload.cardgame.controller;

import de.voidstack_overload.cardgame.SceneFXML;
import de.voidstack_overload.cardgame.network.NetworkManager;
import javafx.application.Platform;
import java.io.IOException;

public class QuitGameScreenController extends BaseController {

    public void switchToMenu() {
        try {
            sceneManager.switchScene(SceneFXML.MENU);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void quitGame() {
        NetworkManager.INSTANCE.disconnect();
        Platform.exit();
        System.exit(0);
    }
}
