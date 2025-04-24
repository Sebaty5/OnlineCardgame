package de.voidstack_overload.cardgame.controller;

import de.voidstack_overload.cardgame.SceneFXML;
import java.io.IOException;

public class SettingsController extends BaseController {

    public void switchToMenu() {
        try {
            sceneManager.switchScene(SceneFXML.MENU);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
