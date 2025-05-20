package de.voidstack_overload.cardgame.actions;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.network.Response;
import org.java_websocket.WebSocket;

public interface Action {
    Response execute(JsonObject json, WebSocket connection);
}
