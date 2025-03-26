package de.voidstack_overload.cardgame.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class CreateLobbyController extends BaseController {

    @FXML
    private TextField lobbyName;

    @FXML
    private TextField lobbyPassword;

    @FXML
    private Slider maxPlayerSlider;

    @FXML
    private Slider botSlider;

    @FXML
    private Button createLobbyButton;

    @FXML
    public void handleCreateLobby() {

    }
}
