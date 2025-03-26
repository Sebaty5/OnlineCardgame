package de.voidstack_overload.cardgame.controller;

import de.voidstack_overload.cardgame.connection.ConnectionManager;
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
    private Slider botAmountSlider;

    @FXML
    private Button createLobbyButton;

    @FXML
    public void handleCreateLobby() {
        String name = lobbyName.getText();
        String password = lobbyPassword.getText();
        int maxPlayers = (int) maxPlayerSlider.getValue();
        int botAmount = (int) botAmountSlider.getValue();
        System.out.println("Create Lobby");
        ConnectionManager.getInstance().lobbyCreate(name, password, maxPlayers, botAmount);
    }
}
