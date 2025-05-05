package de.voidstack_overload.cardgame.controller;

import de.voidstack_overload.cardgame.SceneFXML;

import de.voidstack_overload.cardgame.network.NetworkManager;
import de.voidstack_overload.cardgame.service.AuthenticationService;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;

public class LoginController extends BaseController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    public void confirmLogin() {
        String username = this.username.getText();
        String password = this.password.getText();
        if (username.isEmpty() || password.isEmpty()) {
            showError("Username or password cannot be empty");
            return;
        }
        AuthenticationService.INSTANCE.login(username, password);
    }

    public void switchToRegistration() {
        try {
            sceneManager.switchScene(SceneFXML.REGISTER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void switchToMainMenu() {
        try {
            sceneManager.switchScene(SceneFXML.MENU);
            NetworkManager.INSTANCE.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
