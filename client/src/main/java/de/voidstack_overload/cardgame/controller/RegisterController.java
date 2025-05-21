package de.voidstack_overload.cardgame.controller;

import de.voidstack_overload.cardgame.SceneFXML;
import de.voidstack_overload.cardgame.service.AuthenticationService;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.ResourceBundle;

public class RegisterController extends BaseController {

    @FXML
    private TextField name;

    @FXML
    private TextField email;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private ResourceBundle resources;

    public void confirmRegistration() {
        String username = this.username.getText();
        String password = this.password.getText();
        if (username.isEmpty() || password.isEmpty()) {
            showError(resources.getString("login.error"));
            return;
        }

        AuthenticationService.INSTANCE.register(username, password);
    }

    public void switchToLogin() {
        try {
            sceneManager.switchScene(SceneFXML.LOGIN);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
