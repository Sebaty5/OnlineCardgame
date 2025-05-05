package de.voidstack_overload.cardgame.actions.account;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.SceneFXML;
import de.voidstack_overload.cardgame.actions.BaseAction;
import de.voidstack_overload.cardgame.application.GameClient;
import de.voidstack_overload.cardgame.messages.OutgoingMessageType;
import de.voidstack_overload.cardgame.service.AuthenticationService;
import de.voidstack_overload.cardgame.utility.JsonBuilder;
import de.voidstack_overload.cardgame.utility.User;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.IOException;

public abstract class AccountAction extends BaseAction {

    @Override
    public void execute(JsonObject json) {
        super.execute(json);
    }

    protected void accepted(JsonObject json, OutgoingMessageType type) {
        if(this.lastSendMessage.messageType() != type) {
            LOGGER.error("Server acknowledged " + type + " without client having requested " + type +".");
            return;
        }
        String username = "";
        String password = "";
        if(json.has("username")) {
            username = json.get("username").getAsString();
        }
        if(json.has("password")) {

            password = json.get("password").getAsString();
        }
        validateServerResponse(username, password, type);
        AuthenticationService.INSTANCE.setUser(new User(username));
        Platform.runLater(() -> {
            try {
                GameClient.getSceneManager().switchScene(SceneFXML.PROFILE);
            } catch (IOException ex) {
                LOGGER.error(ex.getMessage());
            }
        });
    }

    protected void validateServerResponse(String username, String password, OutgoingMessageType type) {
        JsonObject lastMessageBody = JsonBuilder.parseJsonString(this.lastSendMessage.messageBody());
        if(lastMessageBody != null && lastMessageBody.has("username") && lastMessageBody.has("password")) {
            if(!username.equals(lastMessageBody.get("username").getAsString())) {
                LOGGER.error("Server returned incorrect username: " + username);
            }
            if(!password.equals(lastMessageBody.get("password").getAsString()) && type == OutgoingMessageType.ACCOUNT_REGISTER) {
                LOGGER.error("Server returned incorrect password: " + password);
            }
        }
    }

    protected void denied(JsonObject json, OutgoingMessageType type) {
        if(this.lastSendMessage.messageType() != type) {
            LOGGER.error("Server denied " + type + " without client having requested " + type +".");
            return;
        }

        if(json.has("errorMessage")) {
            String message = json.get("errorMessage").getAsString();
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(message);
                alert.showAndWait();
            });
        } else {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Request denied.");
                alert.showAndWait();
            });
        }


    }
}
