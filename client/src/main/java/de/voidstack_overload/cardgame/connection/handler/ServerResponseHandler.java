package de.voidstack_overload.cardgame.connection.handler;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.connection.ResponseEntity;

public interface ServerResponseHandler {
    boolean canHandle(String type);
    ResponseEntity<?> handleMessage(JsonObject message);
}
