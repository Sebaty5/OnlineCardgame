package de.voidstack_overload.cardgame.objects;

import org.java_websocket.WebSocket;

public record Player(WebSocket socket, String username) {
}
