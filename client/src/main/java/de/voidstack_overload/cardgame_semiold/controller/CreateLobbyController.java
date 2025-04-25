package de.voidstack_overload.cardgame_semiold.controller;

import de.voidstack_overload.cardgame_semiold.SceneFXML;
import de.voidstack_overload.cardgame_semiold.connection.ConnectionManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import java.io.IOException;

public class CreateLobbyController extends BaseController {

    @FXML
    private TextField lobbyName;

    @FXML
    private PasswordField lobbyPassword;

    @FXML
    private Slider maxPlayers;

    @FXML
    private Slider botAmount;

    public void createLobby() {
        String name = lobbyName.getText();
        String password = lobbyPassword.getText();
        int maxPlayers = (int) this.maxPlayers.getValue();
        int botAmount = (int) this.botAmount.getValue();
        System.out.println("Create Lobby");
        ConnectionManager.getInstance().lobbyCreate(name, password, maxPlayers, botAmount);
    }

    public void switchToMenu() {
        try {
            sceneManager.switchScene(SceneFXML.MENU);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
