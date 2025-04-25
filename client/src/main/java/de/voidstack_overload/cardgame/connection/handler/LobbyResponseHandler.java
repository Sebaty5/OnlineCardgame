package de.voidstack_overload.cardgame.connection.handler;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.connection.ResponseEntity;
import de.voidstack_overload.cardgame.dto.response.ErrorResponse;
import de.voidstack_overload.cardgame.dto.response.LobbyResponse;
import de.voidstack_overload.cardgame.utility.GsonUtil;

public class LobbyResponseHandler extends TypedResponseHandler {

    public LobbyResponseHandler() {
        super("LOBBY");
    }

    @Override
    public ResponseEntity<LobbyResponse> handleMessage(JsonObject message) {
        switch (messageType) {
            case LOBBY_CREATE_ACCEPT -> {
                LobbyResponse lobbyResponse = GsonUtil.toObject(message, LobbyResponse.class);
                logger.log("Lobby erstellt! Lobby-ID: " + lobbyResponse.lobbyID());
                return ResponseEntity.ok(lobbyResponse);
            }
            case LOBBY_CREATE_DENY -> {
                return ResponseEntity.error(new ErrorResponse("Lobby konnte nicht erstellt werden.").errorMessage());
            }
            default -> {
                return null;
            }
        }
    }
}
