package de.voidstack_overload.cardgame.controller;

import de.voidstack_overload.cardgame.SceneFXML;
import de.voidstack_overload.cardgame.service.AuthenticationService;
import de.voidstack_overload.cardgame.connection.ResponseEntity;
import de.voidstack_overload.cardgame.dto.response.AuthenticationResponse;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;

public class LoginController extends BaseController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    private final AuthenticationService authenticationService = new AuthenticationService();

    public void confirmLogin() {
        String username = this.username.getText();
        String password = this.password.getText();
        if (username.isEmpty() || password.isEmpty()) {
            showError("Username or password cannot be empty");
            return;
        }

        ResponseEntity<AuthenticationResponse> response = authenticationService.login(username, password);

        if (response.isSuccess()) {
            acceptLogin();
        } else {
            showError(response.getErrorMessage());
        }
    }

    public void switchToRegistration() {
        try {
            sceneManager.switchScene(SceneFXML.REGISTER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void acceptLogin() {
        try {
            sceneManager.switchScene(SceneFXML.PROFILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
