package de.voidstack_overload.cardgame.controller;

import java.io.IOException;
import de.voidstack_overload.cardgame.configuration.Config;
import de.voidstack_overload.cardgame.connection.ConnectionManager;
import javafx.application.Platform;

public class HomeScreenController extends BaseController {

    public void switchToSettings() {
        System.out.println("Settings");
    }

    public void onPlayClicked() {
        ConnectionManager.getInstance().setServerUri(Config.getInstance().getServerUri());
        ConnectionManager.getInstance().connect();

        try {
            sceneManager.switchScene("LoginScreen.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToCredits() {
        System.out.println("Credits");
    }

    public void exitGame() {
        if (ConnectionManager.getInstance().isConnected()) ConnectionManager.getInstance().disconnect();
        Platform.exit();
    }
}
