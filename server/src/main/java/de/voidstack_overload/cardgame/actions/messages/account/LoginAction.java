package de.voidstack_overload.cardgame.actions.messages.account;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.database.DataBaseHandler;
import de.voidstack_overload.cardgame.messages.OutgoingMessageType;
import de.voidstack_overload.cardgame.network.PlayerManager;
import de.voidstack_overload.cardgame.objects.Player;
import de.voidstack_overload.cardgame.objects.Response;
import de.voidstack_overload.cardgame.utility.JsonBuilder;
import de.voidstack_overload.cardgame.utility.ResponseBuilder;
import org.java_websocket.WebSocket;

public class LoginAction extends AccountAction {
    @Override
    public Response execute(JsonObject json, WebSocket connection) {
        JsonBuilder jsonBuilder = new JsonBuilder();
        if(json.get("username") == null || json.get("password") == null) {
            jsonBuilder.add("errorMessage", "Missing parameters for login");
            return ResponseBuilder.build(OutgoingMessageType.ERROR, jsonBuilder);
        }
        String username = json.get("username").getAsString();
        String password = json.get("password").getAsString();
        if(!DataBaseHandler.INSTANCE.isValidLogin(username, password)) {
            jsonBuilder.add("errorMessage", "Username or password is incorrect");
            return ResponseBuilder.build(OutgoingMessageType.ACCOUNT_LOGIN_DENY, jsonBuilder);
        }
        if (!PlayerManager.INSTANCE.addPlayer(new Player(connection, username))) {
            jsonBuilder.add("errorMessage", "Already logged in");
            return ResponseBuilder.build(OutgoingMessageType.ACCOUNT_LOGIN_DENY, jsonBuilder);
        }
        jsonBuilder.add("username", username);
        return ResponseBuilder.build(OutgoingMessageType.ACCOUNT_LOGIN_ACCEPT, jsonBuilder);
    }
}
