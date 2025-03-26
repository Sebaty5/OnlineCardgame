package de.voidstack_overload.cardgame.controller;

import de.voidstack_overload.cardgame.configuration.Config;
import de.voidstack_overload.cardgame.connection.ConnectionManager;
import javafx.application.Platform;

import java.io.IOException;

public class HomeScreenController extends BaseController {

    public void switchToSettings() {
        System.out.println("Settings");
    }

    public void switchToLobbys() {
        System.out.println("Lobbys");
        ConnectionManager.getInstance().setServerUri(Config.getInstance().getServerUri());
        ConnectionManager.getInstance().connect();
        try {
            sceneManager.switchScene("CreateLobbyScreen.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToCredits() {
        System.out.println("Credits");
    }

    public void exitGame() {
        if(ConnectionManager.getInstance().isConnected()) ConnectionManager.getInstance().disconnect();
        Platform.exit();
    }
}
