package de.voidstack_overload.cardgame.actions.messages.game;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.actions.Action;
import de.voidstack_overload.cardgame.messages.OutgoingMessageType;
import de.voidstack_overload.cardgame.network.PlayerManager;
import de.voidstack_overload.cardgame.objects.Player;
import de.voidstack_overload.cardgame.objects.Response;
import de.voidstack_overload.cardgame.utility.JsonBuilder;
import de.voidstack_overload.cardgame.utility.ResponseBuilder;
import org.java_websocket.WebSocket;

public abstract class GameAction implements Action {
    @Override
    public Response execute(JsonObject json, WebSocket connection) {
        Player player = PlayerManager.INSTANCE.getPlayer(connection);
        if(player == null) {
            JsonBuilder jsonBuilder = new JsonBuilder();
            jsonBuilder.add("errorMessage", "Not logged in.");
            return ResponseBuilder.build(OutgoingMessageType.INSUFFICIENT_PERMISSIONS, jsonBuilder);
        }
        return null;
    }
}
