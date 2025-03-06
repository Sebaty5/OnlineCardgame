package de.voidstack_overload.cardgame.controller_OLD;

import de.voidstack_overload.cardgame.utility.FXMLNavigator;
import de.voidstack_overload.cardgame.application.GameClient;
import de.voidstack_overload.cardgame.PaneResizer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements ControllerInterface {


    @FXML
    private TextField iPAddress;

    @FXML
    private TextField portField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @Override
    public void init() {
        Stage stage = GameClient.stage;
        Scene scene = stage.getScene();
        PaneResizer.resizePane(scene.getHeight(), true);
    }

    @FXML
    private void openHome() {
        FXMLNavigator.loadFxml(FXMLNavigator.HOME);
    }

    @FXML
    private void checkForEntrance() {
        Stage stage = GameClient.stage;
        Scene scene = stage.getScene();

        iPAddress = (TextField) scene.lookup("#IPAddress");
        String ip = iPAddress.getText();

        portField = (TextField) scene.lookup("#Port");
        String port = portField.getText();

        usernameField = (TextField) scene.lookup("#Username");
        String username = usernameField.getText();
        username = username.replace(' ', '_');

        passwordField = (PasswordField) scene.lookup("#Password");
        String password = passwordField.getText();

        boolean isValidIP = validateIP(ip);
        boolean isValidUserName = username.length() > 0;
        boolean isValidPasswort = password.length() > 0;


        if(isValidIP && isValidUserName && isValidPasswort) {
            //TODO start Connection
        }

    }

    private static boolean validateIP(String ip) {
        return ip.matches("^(([01]\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}([01]\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$");
    }


}
