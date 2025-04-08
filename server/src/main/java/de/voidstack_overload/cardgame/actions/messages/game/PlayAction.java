package de.voidstack_overload.cardgame.actions.messages.game;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.game.engine.Board;
import de.voidstack_overload.cardgame.game.engine.cards.Card;
import de.voidstack_overload.cardgame.game.lobby.LobbyManager;
import de.voidstack_overload.cardgame.messages.OutgoingMessageType;
import de.voidstack_overload.cardgame.network.Response;
import de.voidstack_overload.cardgame.network.User;
import de.voidstack_overload.cardgame.network.UserManager;
import de.voidstack_overload.cardgame.utility.JsonBuilder;
import de.voidstack_overload.cardgame.utility.ResponseBuilder;
import org.java_websocket.WebSocket;

public class PlayAction extends GameAction {
    @Override
    public Response execute(JsonObject json, WebSocket connection) {
        Response response = super.execute(json, connection);
        if(response != null) return response;
        if(json.get("card") == null) {
            return ResponseBuilder.errorResponse("Missing arguments for play action.");
        }
        int cardId = json.get("card").getAsInt();

        JsonBuilder jsonBuilder = new JsonBuilder();
        User user = UserManager.INSTANCE.getUser(connection);
        Board board = LobbyManager.INSTANCE.getLobbyOfPlayer(user).getBoard();
        if(board.playCard(Card.intToCard(cardId), board.getPlayerFromUser(user))) {
            return ResponseBuilder.build(OutgoingMessageType.GAME_PLAY_ACCEPT);
        }
        jsonBuilder.add("errorMessage", "Invalid card.");
        return ResponseBuilder.build(OutgoingMessageType.GAME_PLAY_DENY, jsonBuilder);
    }
}
