package de.voidstack_overload.cardgame.actions.messages.lobby;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.messages.OutgoingMessageType;
import de.voidstack_overload.cardgame.game.lobby.LobbyManager;
import de.voidstack_overload.cardgame.network.UserManager;
import de.voidstack_overload.cardgame.objects.User;
import de.voidstack_overload.cardgame.objects.Response;
import de.voidstack_overload.cardgame.utility.ResponseBuilder;
import org.java_websocket.WebSocket;

public class LobbyLeaveAction extends LobbyAction {
    @Override
    public Response execute(JsonObject json, WebSocket connection) {
        Response response = super.execute(json, connection);
        if(response != null) {
            return response;
        }

        User user = UserManager.INSTANCE.getUser(connection);
        LobbyManager.INSTANCE.leaveLobby(user);
        return ResponseBuilder.build(OutgoingMessageType.LOBBY_LEAVE_ACKNOWLEDGE);
    }
}
