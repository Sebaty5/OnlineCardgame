package de.voidstack_overload.cardgame.connection.handler;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.connection.MessageType;
import de.voidstack_overload.cardgame.connection.ResponseEntity;
import de.voidstack_overload.cardgame.model.response.AuthenticationResponse;
import java.util.EnumSet;

public class RegisterResponseHandler extends TypedResponseHandler {

    public RegisterResponseHandler() {
        super(EnumSet.of(MessageType.ACCOUNT_REGISTER_ACCEPT, MessageType.ACCOUNT_REGISTER_DENY));
    }

    @Override
    public ResponseEntity<AuthenticationResponse> handle(JsonObject message) {
        switch (messageType) {
            case ACCOUNT_REGISTER_ACCEPT -> {
                String username = message.get("username").getAsString();
                logger.log("Registrierung erfolgreich! Benutzername: " + username);
                return ResponseEntity.ok(new AuthenticationResponse(username));
            }
            case ACCOUNT_REGISTER_DENY -> {
                String errorMessage = message.get("errorMessage").getAsString();
                logger.log("Registrierung fehlgeschlagen: " + errorMessage);
                return ResponseEntity.error(errorMessage);
            }
        }

        return null;
    }
}
