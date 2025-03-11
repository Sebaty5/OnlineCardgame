package de.voidstack_overload.cardgame.actions.messages.account;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.database.DataBaseHandler;
import de.voidstack_overload.cardgame.messages.OutgoingMessageType;
import de.voidstack_overload.cardgame.network.UserManager;
import de.voidstack_overload.cardgame.network.User;
import de.voidstack_overload.cardgame.network.Response;
import de.voidstack_overload.cardgame.utility.JsonBuilder;
import de.voidstack_overload.cardgame.utility.ResponseBuilder;
import org.java_websocket.WebSocket;

public class LoginAction extends AccountAction {
    @Override
    public Response execute(JsonObject json, WebSocket connection) {
        if(json.get("username") == null || json.get("password") == null) {
            return ResponseBuilder.errorResponse("Missing parameters for login");
        }
        String username = json.get("username").getAsString();
        String password = json.get("password").getAsString();
        JsonBuilder jsonBuilder = new JsonBuilder();
        if(!DataBaseHandler.INSTANCE.isValidLogin(username, password)) {
            jsonBuilder.add("errorMessage", "Username or password is incorrect");
            return ResponseBuilder.build(OutgoingMessageType.ACCOUNT_LOGIN_DENY, jsonBuilder);
        }
        if (!UserManager.INSTANCE.addUser(new User(connection, username, password))) {
            jsonBuilder.add("errorMessage", "Already logged in");
            return ResponseBuilder.build(OutgoingMessageType.ACCOUNT_LOGIN_DENY, jsonBuilder);
        }
        jsonBuilder.add("username", username);
        return ResponseBuilder.build(OutgoingMessageType.ACCOUNT_LOGIN_ACCEPT, jsonBuilder);
    }
}
