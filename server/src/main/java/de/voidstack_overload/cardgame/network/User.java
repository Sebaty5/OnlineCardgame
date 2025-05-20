package de.voidstack_overload.cardgame.network;

import org.java_websocket.WebSocket;

import java.util.Objects;

public class User {
    private final WebSocket webSocket;
    private String username;
    private String password;

    public User(WebSocket webSocket, String name, String password) {
        this.webSocket = webSocket;
        this.username = name;
        this.password = password;
    }

    public WebSocket getWebSocket() {
        return webSocket;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User user)) return false;
        if(webSocket == null) {
             if(user.webSocket == null) return Objects.equals(this.username, user.username);
             else return false;
        }
        return webSocket.equals(user.getWebSocket());
    }

    @Override
    public int hashCode() {
        return Objects.hash(webSocket, username, password);
    }
}
