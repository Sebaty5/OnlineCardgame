package de.voidstack_overload.cardgame.actions.messages.account;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.network.LobbyManager;
import de.voidstack_overload.cardgame.network.PlayerManager;
import de.voidstack_overload.cardgame.objects.Player;
import de.voidstack_overload.cardgame.objects.Response;
import org.java_websocket.WebSocket;

public class LogoutAction extends AccountAction {
    @Override
    public Response execute(JsonObject json, WebSocket connection) {
        Player player = PlayerManager.INSTANCE.getPlayer(connection);
        if(player != null) {
            LobbyManager.INSTANCE.leaveLobby(player);
            PlayerManager.INSTANCE.removePlayer(player);
        }
        connection.close();
        return null;
    }
}