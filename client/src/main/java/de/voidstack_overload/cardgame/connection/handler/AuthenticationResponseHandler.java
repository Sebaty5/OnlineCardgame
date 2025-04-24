package de.voidstack_overload.cardgame.connection.handler;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.connection.ResponseEntity;
import de.voidstack_overload.cardgame.dto.response.AuthenticationResponse;
import de.voidstack_overload.cardgame.dto.response.ErrorResponse;
import de.voidstack_overload.cardgame.utility.GsonUtil;

public class AuthenticationResponseHandler extends TypedResponseHandler {

    public AuthenticationResponseHandler() {
        super("ACCOUNT");
    }

    @Override
    public ResponseEntity<?> handleMessage(JsonObject message) {
        switch (messageType) {
            case ACCOUNT_REGISTER_ACCEPT -> {
                return handleAccept(message, "Registrierung");
            }
            case ACCOUNT_REGISTER_DENY -> {
                return handleDeny(message, "Registrierung");
            }
            case ACCOUNT_LOGIN_ACCEPT -> {
                return handleAccept(message, "Login");
            }
            case ACCOUNT_LOGIN_DENY -> {
                return handleDeny(message, "Login");
            }
            default -> {
                return ResponseEntity.error("MessageType wird nicht unterstützt: " + messageType);
            }
        }
    }

    private ResponseEntity<AuthenticationResponse> handleAccept(JsonObject message, String logPrefix) {
        AuthenticationResponse response = GsonUtil.toObject(message, AuthenticationResponse.class);
        logger.log(logPrefix + " erfolgreich! Benutzername: " + response.username());
        return ResponseEntity.ok(response);
    }

    private ResponseEntity<ErrorResponse> handleDeny(JsonObject message, String logPrefix) {
        ErrorResponse response = GsonUtil.toObject(message, ErrorResponse.class);
        logger.log(logPrefix + " fehlgeschlagen: " + response.errorMessage());
        return ResponseEntity.error(response.errorMessage());
    }
}
