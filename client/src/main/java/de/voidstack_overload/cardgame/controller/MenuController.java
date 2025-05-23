package de.voidstack_overload.cardgame.controller;

import java.io.IOException;
import de.voidstack_overload.cardgame.SceneFXML;
import de.voidstack_overload.cardgame.network.NetworkManager;

public class MenuController extends BaseController {

    public void switchToLogin() {
        NetworkManager.INSTANCE.connect();

        try {
            sceneManager.switchScene(SceneFXML.LOGIN);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToSettings() {
        try {
            sceneManager.switchScene(SceneFXML.SETTINGS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToCredits() {
        try {
            sceneManager.switchScene(SceneFXML.CREDIT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToRules() {
        try {
            sceneManager.switchScene(SceneFXML.RULES);
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
