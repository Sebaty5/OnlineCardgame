package de.voidstack_overload.cardgame.controller;

import java.io.IOException;
import de.voidstack_overload.cardgame.SceneFXML;
import de.voidstack_overload.cardgame.configuration.Config;
import de.voidstack_overload.cardgame.connection.ConnectionManager;


public class MenuController extends BaseController {

    public void switchToLogin() {
        ConnectionManager.getInstance().setServerUri(Config.getInstance().getServerUri());
        ConnectionManager.getInstance().connect();

        try {
            sceneManager.switchScene(SceneFXML.LOGIN);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToSettings() {
        System.out.println("Settings");
    }

    public void switchToCredits() {
        try {
            sceneManager.switchScene(SceneFXML.CREDIT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToQuitGame() {
        try {
            sceneManager.switchScene(SceneFXML.QUIT_GAME);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
