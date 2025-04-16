package de.voidstack_overload.cardgame.controller;

import de.voidstack_overload.cardgame.SceneFXML;
import de.voidstack_overload.cardgame.connection.ConnectionManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;

public class LoginController extends BaseController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void initialize() {
        ConnectionManager.getInstance().setLoginController(this);
    }

    public void confirmLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.isEmpty() || password.isEmpty()) {
            showError("Username or password cannot be empty");
            return;
        }
        ConnectionManager.getInstance().login(username, password);
    }

    public void switchToRegistration() {
        try {
            sceneManager.switchScene(SceneFXML.REGISTRATION);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void acceptLogin() {
        try {
            sceneManager.switchScene(SceneFXML.CREATE_LOBBY);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
