package de.voidstack_overload.cardgame.controller;

import de.voidstack_overload.cardgame.connection.ConnectionManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistrationController extends BaseController {

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
    }

    @FXML
    public void initialize() {
        ConnectionManager.getInstance().setRegistrationController(this);
    }


}
