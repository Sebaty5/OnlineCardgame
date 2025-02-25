package de.voidstack_overload.cardgame.actions.messages.account;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.objects.Response;
import org.java_websocket.WebSocket;

public class LoginAction implements AccountAction {
    @Override
    public Response execute(JsonObject json, WebSocket connection) {
        return null;
    }
}
