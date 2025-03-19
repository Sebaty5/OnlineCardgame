package de.voidstack_overload.cardgame.connection;

import com.google.gson.Gson;
import de.voidstack_overload.cardgame.logging.StandardLogger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class ConnectionManager {

    private static ConnectionManager INSTANCE;
    protected final StandardLogger LOGGER = new StandardLogger("Client");

    private WebSocketClient client;
    private String serverUri;
    private boolean isConnected;

    private ConnectionManager() {
        isConnected = false;
    }

    public static ConnectionManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ConnectionManager();
        }
        return INSTANCE;
    }

    public void setServerUri(String serverUri) {
        this.serverUri = serverUri;
    }

    public boolean isConnected() {
        return this.isConnected;
    }



    public void connect() {
        if(isConnected ||serverUri.isEmpty()) return;
        try {
            client = new WebSocketClient(new URI(serverUri)) {
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
            isConnected = true;
        } catch (URISyntaxException | InterruptedException e) {
            LOGGER.log("Failed to connect: " + e.getMessage());
        }
    }

    public void disconnect() {
        if(client == null || !client.isOpen()) return;
        client.close();
        isConnected = false;
        LOGGER.log("Disconnected from server");
    }




}
