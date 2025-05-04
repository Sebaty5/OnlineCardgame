package de.voidstack_overload.cardgame.network;

import de.voidstack_overload.cardgame.configuration.Config;
import de.voidstack_overload.cardgame.game.lobby.LobbyManager;
import de.voidstack_overload.cardgame.logging.StandardLogger;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

public class NetworkManager extends WebSocketServer {
    private static final StandardLogger LOGGER = new StandardLogger("NetworkManager");

    public static final NetworkManager INSTANCE = new NetworkManager(Config.INSTANCE.getServerPort());

    private NetworkManager(int port) {
        super(new InetSocketAddress(port));
    }

    public void init() {
        this.start();
    }

    @Override
    public void onOpen(WebSocket clientConnection, ClientHandshake handshake) {
        LOGGER.log("New connection: " + clientConnection.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket clientConnection, String message) {
        Response response = MessageHandler.INSTANCE.handleMessage(message, clientConnection);
        if(response != null) {
            clientConnection.send(response.response());
        }
    }

    @Override
    public void onClose(WebSocket clientConnection, int code, String reason, boolean remote) {
        User user = UserManager.INSTANCE.getUser(clientConnection);
        if (user != null) {
            LOGGER.log("Player disconnected: " + user.getUsername()+ " " + clientConnection.getRemoteSocketAddress());
            LobbyManager.INSTANCE.leaveLobby(user);
            UserManager.INSTANCE.removeUser(user);
            return;
        }
        LOGGER.log("Client disconnected: " + clientConnection.getRemoteSocketAddress());
    }

    @Override
    public void onError(WebSocket clientConnection, Exception ex) {
        LOGGER.error("WebSocket error: " + ex.getMessage());
    }

    @Override
    public void onStart() {
        LOGGER.log("Game server started successfully.");
    }
}