package de.voidstack_overload.cardgame.network;

import de.voidstack_overload.cardgame.configuration.Config;
import de.voidstack_overload.cardgame.logging.StandardLogger;
import de.voidstack_overload.cardgame.objects.Player;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;

public class NetworkManager extends WebSocketServer {
    protected static final StandardLogger LOGGER = new StandardLogger("NetworkManager");

    public static final NetworkManager INSTANCE = new NetworkManager(Config.INSTANCE.getServerPort());

    private NetworkManager(int port) {
        super(new InetSocketAddress(port));
    }

    public void init() {
        this.start();
    }

    @Override
    public void onOpen(WebSocket clientConnection, ClientHandshake handshake) {
        if(PlayerManager.INSTANCE.addPlayer(new Player(clientConnection, "noUsernameDefined"))) {
            LOGGER.log("New connection: " + clientConnection.getRemoteSocketAddress());
            clientConnection.send("Welcome to the server!");
        } else {
            clientConnection.send("User already logged in!");
        }

    }

    @Override
    public void onMessage(WebSocket clientConnection, String message) {
        Player player = PlayerManager.INSTANCE.getPlayer(clientConnection);
        if (player == null) {
            clientConnection.close(1008, "Client not registered!");
        }
        LOGGER.log("Received: " + message);
        String[] parts = message.split(" ", 2);
        String command = parts[0].toUpperCase();

        switch (command) {
            case "CREATE_LOBBY":
                if(LobbyManager.INSTANCE.createLobby(player, "noLobbyName", 2, 0)) {
                    clientConnection.send("lobby created");
                } else {
                    clientConnection.send("lobby creation failed");
                }
                break;
            case "VIEW_LOBBIES":
                clientConnection.send(LobbyManager.INSTANCE.getLobbies());
                break;
            case "JOIN_LOBBY":
                if (parts.length > 1) {
                    if(LobbyManager.INSTANCE.joinLobby(player, parts[1])) {
                        clientConnection.send("joined lobby");
                    } else {
                        clientConnection.send("lobby is in an ongoing game");
                    }
                }
                break;
            case "LEAVE_LOBBY":
                LobbyManager.INSTANCE.leaveLobby(player);
                clientConnection.send("lobby has been left");
                break;
            default:
                clientConnection.send("Unknown command");
        }
    }

    @Override
    public void onClose(WebSocket clientConnection, int code, String reason, boolean remote) {
        Player player = PlayerManager.INSTANCE.getPlayer(clientConnection);
        if (player == null) {
            LOGGER.log("Client disconnected abnormally: " + clientConnection.getRemoteSocketAddress());
            return;
        }
        LobbyManager.INSTANCE.leaveLobby(player);
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