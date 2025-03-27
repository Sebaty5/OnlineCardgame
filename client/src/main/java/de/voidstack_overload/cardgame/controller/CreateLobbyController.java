package de.voidstack_overload.cardgame.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import java.io.IOException;

public class CreateLobbyController extends BaseController {

    @FXML
    private PasswordField lobbyPassword;

    @FXML
    private TextField lobbyName;

    @FXML
    private Slider maxPlayers;

    public void createLobby() {
        System.out.println("Name: " + lobbyName.getText() +
                "\nPassword: " + lobbyPassword.getText() +
                "\nSpieleranzahl: " + maxPlayers.getValue());
    }

    public void switchToHome() throws IOException {
        sceneManager.switchScene("HomeScreen.fxml");
    }
}
