package de.voidstack_overload.cardgame.controller;

import de.voidstack_overload.cardgame.SceneFXML;
import de.voidstack_overload.cardgame.service.AuthenticationService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


import java.io.IOException;

public class ProfileScreenController extends BaseController {
    private final AuthenticationService authenticationService = AuthenticationService.getInstance();

    @FXML
    TextField profileText;

    @FXML
    public void initialize() {
        profileText.setText(authenticationService.getUser() != null ? authenticationService.getUser().username() : "unknown");
    }

    public void switchToLobbySettings() {
        try {
            sceneManager.switchScene(SceneFXML.LOBBY_SETTINGS);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToLobbyList() {
        try {
            sceneManager.switchScene(SceneFXML.LOBBY_LIST);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToMainMenuAndLogout() {
        try {
            authenticationService.logout();
            sceneManager.switchScene(SceneFXML.MENU);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
