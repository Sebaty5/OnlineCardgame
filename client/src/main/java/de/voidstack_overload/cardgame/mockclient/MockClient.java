package de.voidstack_overload.cardgame.mockclient;

import de.voidstack_overload.cardgame.logging.StandardLogger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import com.google.gson.Gson;

public class MockClient {
    protected static final StandardLogger LOGGER = new StandardLogger("MockClient");

    private WebSocketClient client;
    private final String serverUri;
    private static final Gson GSON = new Gson();

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
            String jsonMessage = parseInputToJson(message);
            if(jsonMessage != null) {
                client.send(jsonMessage);
            } else {
                LOGGER.log("Message (" + jsonMessage + ") not send due to invalid command format");
            }
        } else {
            LOGGER.log("Connection not established.");
        }
    }

    private String parseInputToJson(String input) {
        String[] parts = input.split(" ");
        if (parts.length == 0) return null;

        String command = parts[0].toUpperCase();
        Map<String, Object> message = new HashMap<>();
        message.put("type", command);

        switch (command) {
            case "ACCOUNT_LOGIN":
                if (parts.length < 3) return null;
                message.put("username", parts[1]);
                message.put("password", parts[2]);
                break;

            case "ACCOUNT_LOGOUT":
                // Kein Parameter nötig
                break;

            case "ACCOUNT_REGISTER":
                if (parts.length < 3) return null;
                message.put("username", parts[1]);
                message.put("password", parts[2]);
                break;

            case "LOBBY_CREATE":
                if (parts.length < 5) return null;
                message.put("lobbyName", parts[1]);
                message.put("lobbyPassword", parts[2]);
                message.put("maxPlayers", Integer.parseInt(parts[3]));
                message.put("botCount", Integer.parseInt(parts[4]));
                break;

            case "LOBBY_JOIN":
                if (parts.length < 3) return null;
                message.put("lobbyID", parts[1]);
                message.put("lobbyPassword", parts[2]);
                break;

            case "LOBBY_LEAVE":
                break;

            case "LOBBY_LIST":
                break;

            case "GAME_PLAY":
                if (parts.length < 2) return null;
                message.put("card", Integer.parseInt(parts[1]));
                break;

            case "GAME_PASS":
                break;

            default:
                return null;
        }
        return GSON.toJson(message);
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
