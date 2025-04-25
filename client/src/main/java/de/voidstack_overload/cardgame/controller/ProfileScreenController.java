package de.voidstack_overload.cardgame.controller;

import de.voidstack_overload.cardgame.SceneFXML;
import java.io.IOException;

public class ProfileScreenController extends BaseController {

    public void switchToLobbySettings() {
        try {
            sceneManager.switchScene(SceneFXML.LOBBY_SETTINGS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
