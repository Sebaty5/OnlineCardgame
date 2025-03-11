package de.voidstack_overload.cardgame.actions.messages.account;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.logging.StandardLogger;
import de.voidstack_overload.cardgame.game.lobby.LobbyManager;
import de.voidstack_overload.cardgame.network.UserManager;
import de.voidstack_overload.cardgame.network.User;
import de.voidstack_overload.cardgame.network.Response;
import org.java_websocket.WebSocket;

public class LogoutAction extends AccountAction {
    private static final StandardLogger LOGGER = new StandardLogger("LogoutAction");

    @Override
    public Response execute(JsonObject json, WebSocket connection) {
        User user = UserManager.INSTANCE.getUser(connection);
        if(user != null) {
            LobbyManager.INSTANCE.leaveLobby(user);
            if(!UserManager.INSTANCE.removeUser(user)) {
                LOGGER.log("Removal of player " + user.getUsername() + " with IP " + user.getWebSocket().getRemoteSocketAddress() + " failed.");
            }
        }
        connection.close();
        return null;
    }
}