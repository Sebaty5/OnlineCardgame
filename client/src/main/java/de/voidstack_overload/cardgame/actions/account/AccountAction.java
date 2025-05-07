package de.voidstack_overload.cardgame.actions.account;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.SceneFXML;
import de.voidstack_overload.cardgame.actions.BaseAction;
import de.voidstack_overload.cardgame.messages.OutgoingMessageType;
import de.voidstack_overload.cardgame.service.AuthenticationService;
import de.voidstack_overload.cardgame.utility.FxUtility;
import de.voidstack_overload.cardgame.utility.JsonBuilder;
import de.voidstack_overload.cardgame.records.User;

public abstract class AccountAction extends BaseAction {

    @Override
    public void execute(JsonObject json) {
        super.execute(json);
    }

    protected void accepted(JsonObject json, OutgoingMessageType type) {
        if(this.lastSendMessage.messageType() != type) {
            LOGGER.error("Server acknowledged " + type + " without client having requested " + type +".");
            AuthenticationService.INSTANCE.logout();
            FxUtility.switchScene(SceneFXML.MENU);
            FxUtility.showAlert("Server accepted" + type + " without request.\nDisconnecting to clear connection.");
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
        if(!validateServerResponse(username, password, type)) {
            return;
        }
        AuthenticationService.INSTANCE.setUser(new User(username));
        FxUtility.switchScene(SceneFXML.PROFILE);
    }

    protected boolean validateServerResponse(String username, String password, OutgoingMessageType type) {
        JsonObject lastMessageBody = JsonBuilder.parseJsonString(this.lastSendMessage.messageBody());
        if(lastMessageBody == null) {
            return false;
        }
        if(lastMessageBody.has("username") && lastMessageBody.has("password")) {
            if(!username.equals(lastMessageBody.get("username").getAsString())) {
                LOGGER.error("Server returned incorrect username: " + username);
                return false;
            }
            if(!password.equals(lastMessageBody.get("password").getAsString()) && type == OutgoingMessageType.ACCOUNT_REGISTER) {
                LOGGER.error("Server returned incorrect password: " + password);
                return false;
            }
        }
        return true;
    }

    protected void denied(JsonObject json, OutgoingMessageType type) {
        if(this.lastSendMessage.messageType() != type) {
            LOGGER.error("Server denied " + type + " without client having requested " + type +".");
            AuthenticationService.INSTANCE.logout();
            FxUtility.switchScene(SceneFXML.MENU);
            FxUtility.showAlert("Server denied " + type + " without request.\nDisconnecting to clear connection.");
            return;
        }

        String message = "Request denied.";
        if(json.has("errorMessage")) {
             message = json.get("errorMessage").getAsString();
        }
        FxUtility.showErrorAlert(message);
    }
}
