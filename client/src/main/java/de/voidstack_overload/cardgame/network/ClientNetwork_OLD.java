package de.voidstack_overload.cardgame.network;

import de.voidstack_overload.cardgame.logging.StandardLogger;
import org.java_websocket.client.WebSocketClient;

public class ClientNetwork_OLD {

    private WebSocketClient clientSocket;

    private static final StandardLogger LOGGER = new StandardLogger(ClientNetwork_OLD.class.getName());


    private static ClientNetwork_OLD instance;

    private ClientNetwork_OLD() {
        clientSocket = null;
    }

    public static ClientNetwork_OLD getInstance() {
        if (instance == null) {
            instance = new ClientNetwork_OLD();
        }
        return instance;
    }
    /*

    public WebSocketClient getClientSocket() {
        return clientSocket;
    }

    private void connect() {
        try {
            clientSocket = new WebSocketClient(new URI(serverUri)) {
                @Override
                public void onOpen(ServerHandshake handshake) {
                    LOGGER.log("Connected to server");
                }

                @Override
                public void onMessage(String message) {
                    LOGGER.log("Server: " + message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    LOGGER.log("Disconnected: " + reason);
                }

                @Override
                public void onError(Exception ex) {
                    LOGGER.log("Error: " + ex.getMessage());
                }
            };
            client.connectBlocking();
        } catch (URISyntaxException | InterruptedException e) {
            LOGGER.log("Failed to connect: " + e.getMessage());
        }
    }

    private void disconnect() {
        if (client != null && client.isOpen()) {
            client.close();
            LOGGER.log("Disconnected from server.");
        }
    }

    public void sendMessage(String message) {
        if (client == null || !client.isOpen()) {
            connect();
        }
        if (client.isOpen()) {
            client.send(message);
        } else {
            LOGGER.log("Connection not established.");
        }
    }
    */



}
