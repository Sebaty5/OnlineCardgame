package de.voidstack_overload.cardgame.controller;

import de.voidstack_overload.cardgame.SceneFXML;
import de.voidstack_overload.cardgame.connection.ConnectionManager;
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
        if (ConnectionManager.getInstance().isConnected()) ConnectionManager.getInstance().disconnect();
        Platform.exit();
    }
}
