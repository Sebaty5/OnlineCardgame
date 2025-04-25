package de.voidstack_overload.cardgame.actions.messages.game;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.game.engine.Board;
import de.voidstack_overload.cardgame.game.lobby.LobbyManager;
import de.voidstack_overload.cardgame.messages.OutgoingMessageType;
import de.voidstack_overload.cardgame.network.Response;
import de.voidstack_overload.cardgame.network.User;
import de.voidstack_overload.cardgame.network.UserManager;
import de.voidstack_overload.cardgame.utility.JsonBuilder;
import de.voidstack_overload.cardgame.utility.ResponseBuilder;
import org.java_websocket.WebSocket;

public class PassAction extends GameAction {
    @Override
    public Response execute(JsonObject json, WebSocket connection) {
        Response response = super.execute(json, connection);
        if(response != null) return response;
        JsonBuilder jsonBuilder = new JsonBuilder();
        User user = UserManager.INSTANCE.getUser(connection);
        Board board = LobbyManager.INSTANCE.getLobbyOfPlayer(user).getBoard();
        if(board.skip(board.getPlayerFromUser(user))) {
            return ResponseBuilder.build(OutgoingMessageType.GAME_PASS_ACCEPT);
        }
        jsonBuilder.add("errorMessage", "Not your turn.");
        return ResponseBuilder.build(OutgoingMessageType.GAME_PASS_DENY, jsonBuilder);
    }
}
