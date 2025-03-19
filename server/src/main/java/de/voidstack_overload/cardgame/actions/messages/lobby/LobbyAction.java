package de.voidstack_overload.cardgame.actions.messages.lobby;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.actions.Action;
import de.voidstack_overload.cardgame.messages.IncomingMessageType;
import de.voidstack_overload.cardgame.network.UserManager;
import de.voidstack_overload.cardgame.network.User;
import de.voidstack_overload.cardgame.network.Response;
import de.voidstack_overload.cardgame.utility.ResponseBuilder;
import org.java_websocket.WebSocket;

public abstract class LobbyAction implements Action {
    @Override
    public Response execute(JsonObject json, WebSocket connection) {
        User user = UserManager.INSTANCE.getUser(connection);
        if(user == null) {
            return ResponseBuilder.insufficientPermissionResponse(IncomingMessageType.fromString(json.get("type").getAsString()));
        }
        return null;
    }
}
