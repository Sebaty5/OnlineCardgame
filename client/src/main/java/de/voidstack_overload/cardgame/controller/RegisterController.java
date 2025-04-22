package de.voidstack_overload.cardgame.controller;

import de.voidstack_overload.cardgame.SceneFXML;
import de.voidstack_overload.cardgame.connection.ResponseEntity;
import de.voidstack_overload.cardgame.model.response.AuthenticationResponse;
import de.voidstack_overload.cardgame.service.AuthenticationService;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;

public class RegisterController extends BaseController {

    @FXML
    private TextField name;

    @FXML
    private TextField email;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    private final AuthenticationService authenticationService = new AuthenticationService();

    public void confirmRegistration() {
        String username = this.username.getText();
        String password = this.password.getText();
        if (username.isEmpty() || password.isEmpty()) {
            showError("Username or password cannot be empty");
            return;
        }

        ResponseEntity<AuthenticationResponse> response = authenticationService.register(username, password);

        if (response.isSuccess()) {
            acceptRegister();
        } else {
            showError(response.getErrorMessage());
        }
    }

    private void acceptRegister() {
        try {
            sceneManager.switchScene(SceneFXML.CREATE_LOBBY);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
