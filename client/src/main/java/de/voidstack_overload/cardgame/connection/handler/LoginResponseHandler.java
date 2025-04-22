package de.voidstack_overload.cardgame.connection.handler;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.connection.MessageType;
import de.voidstack_overload.cardgame.connection.ResponseEntity;
import de.voidstack_overload.cardgame.model.response.AuthenticationResponse;
import java.util.EnumSet;

public class LoginResponseHandler extends TypedResponseHandler {

    public LoginResponseHandler() {
        super(EnumSet.of(MessageType.ACCOUNT_LOGIN_ACCEPT, MessageType.ACCOUNT_LOGIN_DENY));
    }

    @Override
    public ResponseEntity<AuthenticationResponse> handle(JsonObject message) {
        switch (messageType) {
            case ACCOUNT_LOGIN_ACCEPT -> {
                String username = message.get("username").getAsString();
                logger.log("Login erfolgreich! Benutzername: " + username);
                return ResponseEntity.ok(new AuthenticationResponse(username));
            }
            case ACCOUNT_LOGIN_DENY -> {
                String errorMessage = message.get("errorMessage").getAsString();
                logger.log("Login fehlgeschlagen: " + errorMessage);
                return ResponseEntity.error(errorMessage);
            }
        }

        return null;
    }
}
