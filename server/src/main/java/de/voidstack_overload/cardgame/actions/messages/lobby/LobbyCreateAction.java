package de.voidstack_overload.cardgame.actions.messages.lobby;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.messages.OutgoingMessageType;
import de.voidstack_overload.cardgame.game.lobby.LobbyManager;
import de.voidstack_overload.cardgame.network.UserManager;
import de.voidstack_overload.cardgame.game.lobby.Lobby;
import de.voidstack_overload.cardgame.network.User;
import de.voidstack_overload.cardgame.network.Response;
import de.voidstack_overload.cardgame.utility.JsonBuilder;
import de.voidstack_overload.cardgame.utility.ResponseBuilder;
import org.java_websocket.WebSocket;

public class LobbyCreateAction extends LobbyAction {
    @Override
    public Response execute(JsonObject json, WebSocket connection) {
        Response response = super.execute(json, connection);
        if(response != null) {
               return response;
        }
        JsonBuilder jsonBuilder = new JsonBuilder();
        User user = UserManager.INSTANCE.getUser(connection);
        if(json.get("lobbyName") == null || json.get("lobbyPassword") == null || json.get("maxPlayers") == null || json.get("botCount") == null) {
            return ResponseBuilder.errorResponse("Missing arguments for lobby creation.");
        }
        String lobbyName = json.get("lobbyName").getAsString();
        String lobbyPassword = json.get("lobbyPassword").getAsString();
        int maxPlayers = json.get("maxPlayers").getAsInt();
        int botCount= json.get("botCount").getAsInt();
        Lobby lobby = LobbyManager.INSTANCE.createLobby(user, lobbyName, lobbyPassword, maxPlayers, botCount);
        if(lobby == null) {
            return ResponseBuilder.errorResponse("User already in a lobby.");
        }
        jsonBuilder.add("lobbyID", lobby.getId());
        return ResponseBuilder.build(OutgoingMessageType.LOBBY_CREATE_ACCEPT, jsonBuilder);
    }
}
