package de.voidstack_overload.cardgame.actions.messages.account;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.logging.StandardLogger;
import de.voidstack_overload.cardgame.network.LobbyManager;
import de.voidstack_overload.cardgame.network.PlayerManager;
import de.voidstack_overload.cardgame.objects.Player;
import de.voidstack_overload.cardgame.objects.Response;
import org.java_websocket.WebSocket;

public class LogoutAction extends AccountAction {
    private static StandardLogger LOGGER = new StandardLogger("LogoutAction");

    @Override
    public Response execute(JsonObject json, WebSocket connection) {
        Player player = PlayerManager.INSTANCE.getPlayer(connection);
        if(player != null) {
            LobbyManager.INSTANCE.leaveLobby(player);
            if(!PlayerManager.INSTANCE.removePlayer(player)) {
                LOGGER.log("Removal of player " + player.username() + " with IP " + player.socket().getRemoteSocketAddress() + " failed.");
            }
        }
        connection.close();
        return null;
    }
}