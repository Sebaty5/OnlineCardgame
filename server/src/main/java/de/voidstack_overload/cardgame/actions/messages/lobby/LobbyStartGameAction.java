package de.voidstack_overload.cardgame.actions.messages.lobby;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.game.lobby.Lobby;
import de.voidstack_overload.cardgame.game.lobby.LobbyManager;
import de.voidstack_overload.cardgame.messages.OutgoingMessageType;
import de.voidstack_overload.cardgame.network.Response;
import de.voidstack_overload.cardgame.network.User;
import de.voidstack_overload.cardgame.network.UserManager;
import de.voidstack_overload.cardgame.utility.JsonBuilder;
import de.voidstack_overload.cardgame.utility.ResponseBuilder;
import org.java_websocket.WebSocket;

public class LobbyStartGameAction extends LobbyAction {
    @Override
    public Response execute(JsonObject json, WebSocket connection) {
        Response response = super.execute(json, connection);
        if(response != null) {
            return response;
        }
        User user = UserManager.INSTANCE.getUser(connection);
        JsonBuilder builder = new JsonBuilder();
        if(!LobbyManager.INSTANCE.isPlayerInLobby(user)) {
            builder.add("errorMessage", "You are not in a lobby!");
            response = ResponseBuilder.build(OutgoingMessageType.LOBBY_START_GAME_DENY, builder);
            return response;
        }
        Lobby lobby = LobbyManager.INSTANCE.getLobbyOfPlayer(user);
        if(lobby.getHost().equals(user)) {
            lobby.startGame();
            response = ResponseBuilder.build(OutgoingMessageType.LOBBY_START_GAME_ACCEPT);
        } else {
            builder.add("errorMessage", "You are not the host of your lobby!");
            response = ResponseBuilder.build(OutgoingMessageType.LOBBY_START_GAME_DENY);
        }
        return response;
    }
}
