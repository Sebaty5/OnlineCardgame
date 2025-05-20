package de.voidstack_overload.cardgame.actions.messages.lobby;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.game.lobby.Lobby;
import de.voidstack_overload.cardgame.game.lobby.LobbyManager;
import de.voidstack_overload.cardgame.messages.OutgoingMessageType;
import de.voidstack_overload.cardgame.network.Response;
import de.voidstack_overload.cardgame.network.User;
import de.voidstack_overload.cardgame.network.UserManager;
import de.voidstack_overload.cardgame.utility.ResponseBuilder;
import org.java_websocket.WebSocket;

public class LobbyRequestDataAction extends LobbyAction {
    @Override
    public Response execute(JsonObject json, WebSocket connection) {
        Response response = super.execute(json, connection);
        if(response != null) {
            return response;
        }
        User user = UserManager.INSTANCE.getUser(connection);
        if(!LobbyManager.INSTANCE.isPlayerInLobby(user)) {
            response = ResponseBuilder.errorResponse("You are not in a lobby!");
            return response;
        }
        Lobby lobby = LobbyManager.INSTANCE.getLobbyOfPlayer(user);
        lobby.sendLobbyData();
        response = ResponseBuilder.build(OutgoingMessageType.LOBBY_REQUEST_DATA_ACKNOWLEDGE);
        return response;
    }
}
