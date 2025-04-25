package de.voidstack_overload.cardgame_semiold.controller;

import java.io.IOException;
import de.voidstack_overload.cardgame_semiold.SceneFXML;
import de.voidstack_overload.cardgame_semiold.configuration.Config;
import de.voidstack_overload.cardgame_semiold.connection.ConnectionManager;


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
