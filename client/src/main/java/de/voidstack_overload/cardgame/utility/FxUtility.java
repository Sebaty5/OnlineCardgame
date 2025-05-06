package de.voidstack_overload.cardgame.utility;

import de.voidstack_overload.cardgame.SceneFXML;
import de.voidstack_overload.cardgame.application.GameClient;
import de.voidstack_overload.cardgame.logging.StandardLogger;
import de.voidstack_overload.cardgame.service.LobbyService;
import de.voidstack_overload.cardgame.utility.dialog.VoidStackDialog;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class FxUtility {
    private static final StandardLogger LOGGER = new StandardLogger();

    public static void showAlert(String content) {
        showCustomDialog(content);
    }

    public static void showErrorAlert(String errorMessage) {
        showCustomDialog(errorMessage);
    }

    public static void switchScene(SceneFXML sceneFXML) {
        Platform.runLater(() -> {
            try {
                GameClient.getSceneManager().switchScene(sceneFXML);
            } catch (IOException ex) {
                LOGGER.error(ex.getMessage());
            }
        });
    }

    public static void showCustomDialog(String message) {
        Platform.runLater(() -> {
            ButtonType OK = new ButtonType("OK");
            VoidStackDialog dialog = new VoidStackDialog(message, OK);
            DialogPane pane = dialog.getDialogPane();
            Stage stage = (Stage) pane.getScene().getWindow();
            stage.initStyle(StageStyle.TRANSPARENT);
            pane.getScene().setFill(Color.TRANSPARENT);
            dialog.showAndWait();
        });
    }

    public static void showPasswordPrompt(String lobbyID) {
        Platform.runLater(() -> {
            ButtonType OK = new ButtonType("OK");
            ButtonType CANCEL = new ButtonType("CANCEL");
            VoidStackDialog dialog = new VoidStackDialog("Please enter the password for this lobby.", OK, CANCEL);
            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Password");
            passwordField.getStyleClass().add("inputField");
            dialog.addContent(passwordField);

            DialogPane pane = dialog.getDialogPane();
            Stage stage = (Stage) pane.getScene().getWindow();
            stage.initStyle(StageStyle.TRANSPARENT);
            pane.getScene().setFill(Color.TRANSPARENT);
            ButtonType choice = dialog
                    .showAndWait()
                    .orElse(CANCEL);
            if (choice == OK) {
                LobbyService.lobbyJoin(lobbyID, passwordField.getText());
            }
        });
    }
}
