package de.voidstack_overload.cardgame.mockclient;

import de.voidstack_overload.cardgame.logging.StandardLogger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class MockClient {
    protected static final StandardLogger LOGGER = new StandardLogger("MockClient");

    private WebSocketClient client;
    private final String serverUri;

    public MockClient(String serverUri) {
        this.serverUri = serverUri;
    }

    private void connect() {
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

    public static void main(String[] args) {
        MockClient mockClient = new MockClient("ws://localhost:8080");
        Scanner scanner = new Scanner(System.in);

        LOGGER.log("Enter commands (CREATE_LOBBY, VIEW_LOBBIES, JOIN_LOBBY <id>, LEAVE_LOBBY, HELP, EXIT):");
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("EXIT")) {
                mockClient.disconnect();
                break;
            } else if(input.equalsIgnoreCase("HELP")) {
                LOGGER.log("Enter commands (CREATE_LOBBY, VIEW_LOBBIES, JOIN_LOBBY <id>, LEAVE_LOBBY, HELP, EXIT):");
            } else {
                mockClient.sendMessage(input);
            }
        }
    }
}
