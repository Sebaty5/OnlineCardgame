package de.voidstack_overload.cardgame.actions.messages.lobby;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.game.lobby.LobbyManager;
import de.voidstack_overload.cardgame.messages.OutgoingMessageType;
import de.voidstack_overload.cardgame.network.Response;
import de.voidstack_overload.cardgame.utility.JsonBuilder;
import de.voidstack_overload.cardgame.utility.ResponseBuilder;
import org.java_websocket.WebSocket;

public class LobbyListAction extends LobbyAction {
    @Override
    public Response execute(JsonObject json, WebSocket connection) {
        Response response = super.execute(json, connection);
        if(response != null) {
            return response;
        }

        JsonBuilder builder = new JsonBuilder();
        builder.add("lobbyList" ,LobbyManager.INSTANCE.getLobbies());

        return ResponseBuilder.build(OutgoingMessageType.LOBBY_LIST_REPLY, builder);
    }
}
