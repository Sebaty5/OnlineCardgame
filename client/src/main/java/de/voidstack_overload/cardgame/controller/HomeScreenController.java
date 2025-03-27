package de.voidstack_overload.cardgame.controller;

import java.io.IOException;

public class HomeScreenController extends BaseController {

    public void switcheToSettings() {
        System.out.println("Settings");
    }

    public void switchToLobbys() throws IOException {
        sceneManager.switchScene("CreateLobbyScreen.fxml");
    }

    public void switchToCredits() {
        System.out.println("Credits");
    }

    public void exitGame() {
        System.out.println("Bye bye");
    }
}
