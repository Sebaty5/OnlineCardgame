package de.voidstack_overload.cardgame.controller;

import de.voidstack_overload.cardgame.SceneFXML;
import de.voidstack_overload.cardgame.connection.ConnectionManager;
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

    public void confirmRegistration() {
        if (username.getLength() == 0 || password.getLength() == 0) {
            showError("Username or password cannot be empty");
            return;
        }
        ConnectionManager.getInstance().register(username.getText(), password.getText());
        try {
            sceneManager.switchScene(SceneFXML.PROFILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize() {
        ConnectionManager.getInstance().setRegistrationController(this);
    }
}
