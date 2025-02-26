package de.voidstack_overload.cardgame.actions.messages.account;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.database.DataBaseHandler;
import de.voidstack_overload.cardgame.messages.MessageTypeServer;
import de.voidstack_overload.cardgame.objects.Response;
import de.voidstack_overload.cardgame.utility.JsonBuilder;
import de.voidstack_overload.cardgame.utility.ResponseBuilder;
import org.java_websocket.WebSocket;

public class RegisterAction implements AccountAction {
    @Override
    public Response execute(JsonObject json, WebSocket connection) {
        JsonBuilder jsonBuilder = new JsonBuilder();
        String username = json.get("username").getAsString();
        String password = json.get("password").getAsString();
        if(DataBaseHandler.INSTANCE.isRegisteredUser(username)) {
            jsonBuilder.add("errorMessage", "Username is already in use");
            return ResponseBuilder.build(MessageTypeServer.ACCOUNT_REGISTER_DENY, jsonBuilder);
        }
        jsonBuilder.add("username", username);
        jsonBuilder.add("password", password);
        DataBaseHandler.INSTANCE.registerUser(username, password);
        return ResponseBuilder.build(MessageTypeServer.ACCOUNT_REGISTER_ACCEPT, jsonBuilder);
    }
}
