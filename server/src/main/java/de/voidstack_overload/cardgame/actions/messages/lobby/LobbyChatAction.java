package de.voidstack_overload.cardgame.actions.messages.lobby;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.game.engine.Player;
import de.voidstack_overload.cardgame.game.lobby.Lobby;
import de.voidstack_overload.cardgame.game.lobby.LobbyManager;
import de.voidstack_overload.cardgame.network.Response;
import de.voidstack_overload.cardgame.network.User;
import de.voidstack_overload.cardgame.network.UserManager;
import de.voidstack_overload.cardgame.utility.ResponseBuilder;
import org.java_websocket.WebSocket;

public class LobbyChatAction extends LobbyAction
{
    @Override
    public Response execute(JsonObject json, WebSocket connection)
    {
        User user = UserManager.INSTANCE.getUser(connection);
        if(user == null)
        {
            return ResponseBuilder.errorResponse("You are not connected to the server? I have no idea how you may have managed this.");
        }
        Lobby lobby = LobbyManager.INSTANCE.getLobbyOfPlayer(user);
        if (lobby == null)
        {
            return ResponseBuilder.errorResponse("You are not in a lobby to send chat Messages.");
        }
        Player player = null;
        for (Player p : lobby.getPlayers())
        {
            if (p.equals(user))
            {
                player = p;
                break;
            }
        }
        if (player == null)
        {
            return ResponseBuilder.errorResponse("You are not in a lobby to send chat Messages.");
        }

        lobby.broadcast(player.getUsername() + ": " + json.get("message").getAsString());
        return null;
    }
}