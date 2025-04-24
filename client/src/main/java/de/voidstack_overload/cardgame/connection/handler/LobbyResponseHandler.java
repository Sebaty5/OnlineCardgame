package de.voidstack_overload.cardgame.connection.handler;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.connection.MessageType;
import de.voidstack_overload.cardgame.connection.ResponseEntity;
import de.voidstack_overload.cardgame.model.response.LobbyResponse;
import java.util.EnumSet;

public class LobbyResponseHandler extends TypedResponseHandler {

    public LobbyResponseHandler() {
        super("LOBBY");
    }

    @Override
    public ResponseEntity<LobbyResponse> handleMessage(JsonObject message) {
        switch (messageType) {
            case LOBBY_CREATE_ACCEPT -> {
                String lobbyID = message.get("lobbyID").getAsString();
                logger.log("Lobby erstellt! Lobby-ID: " + lobbyID);
                return ResponseEntity.ok(new LobbyResponse(lobbyID));
            }
            case LOBBY_CREATE_DENY -> {
                String errorMessage = "Lobby konnte nicht erstellt werden.";
                return ResponseEntity.error(errorMessage);
            }
            default -> {
                return null;
            }
        }
    }
}
