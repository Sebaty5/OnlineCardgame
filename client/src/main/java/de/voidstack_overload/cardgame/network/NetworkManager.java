package de.voidstack_overload.cardgame.network;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.voidstack_overload.cardgame.configuration.Config;
import de.voidstack_overload.cardgame.logging.StandardLogger;

import java.net.URI;
import java.net.URISyntaxException;

public class NetworkManager {
    protected static final StandardLogger LOGGER = new StandardLogger("Network Manager");
    public final static NetworkManager INSTANCE = new NetworkManager();

    private NetworkClient client;

    private final URI serverUri;

    private Message lastSentMessage = null;
    public Message getLastSentMessage() {
        return lastSentMessage;
    }

    private NetworkManager() {
        try {
            serverUri = new URI(Config.getInstance().getServerUri());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        client = new NetworkClient(serverUri);
    }

    public void connect() {
        client = new NetworkClient(serverUri);
        try {
        this.client.connectBlocking();
        } catch (InterruptedException e) {
            LOGGER.error("Failed to connect: " + e.getMessage());
        }
    }

    public void sendMessage(Message message) {
        if (client == null || !client.isOpen()) {
            connect();
        }
        if (client != null && client.isOpen()) {
            String jsonMessage = message.messageBody();
            if(jsonMessage != null) {
                client.send(jsonMessage);
                lastSentMessage = message;
            } else {
                LOGGER.error("Message not send due to being NULL");
            }
        } else {
            LOGGER.error("Connection not established.");
        }
    }

    public void disconnect() {
        if (client == null || !client.isOpen()) {
            LOGGER.log("Client was no connected");
            return;
        }
        client.close();
        LOGGER.log("Disconnected from server");
    }

    private void handleServerMessage(String message) {
        try {
            JsonObject jsonNode = JsonParser.parseString(message).getAsJsonObject();
            String type = jsonNode.get("type").getAsString();
            String errorMessage;
            switch (type) {
//                case "ACCOUNT_LOGIN_ACCEPT":
//                    LOGGER.log("Login erfolgreich! Benutzername: " + jsonNode.get("username").getAsString());
//                    Platform.runLater(() -> {
//                        if (loginController != null) {
//                            loginController.acceptLogin();
//                        }
//                    });
//                    break;
//
//                case "ACCOUNT_LOGIN_DENY":
//                    errorMessage = jsonNode.get("errorMessage").getAsString();
//                    LOGGER.log("Login fehlgeschlagen: " + errorMessage);
//                    Platform.runLater(() -> {
//                        if (loginController != null) {
//                            loginController.showError(errorMessage);
//                        }
//                    });
//                    break;
//
//                case "ACCOUNT_REGISTER_ACCEPT":
//                    LOGGER.log("Registrierung erfolgreich! Benutzername: " + jsonNode.get("username").getAsString());
//                    break;
//
//                case "ACCOUNT_REGISTER_DENY":
//                    errorMessage = jsonNode.get("errorMessage").getAsString();
//                    LOGGER.log("Registrierung fehlgeschlagen: " + errorMessage);
//                    Platform.runLater(() -> {
//                        if (registrationController != null) {
//                            registrationController.showError(errorMessage);
//                        }
//                    });
//                    break;

                case "LOBBY_CREATE_ACCEPT":
                    LOGGER.log("Lobby erstellt! Lobby-ID: " + jsonNode.get("lobbyID").getAsString());
                    break;

                case "LOBBY_CREATE_DENY":
                    LOGGER.log("Lobby konnte nicht erstellt werden.");
                    break;

                case "LOBBY_JOIN_ACCEPT":
                    LOGGER.log("Lobby beigetreten.");
                    break;

                case "LOBBY_JOIN_DENY":
                    LOGGER.log("Lobby-Beitritt fehlgeschlagen: " + jsonNode.get("errorMessage").getAsString());
                    break;

                case "LOBBY_LIST_REPLY":
                    LOGGER.log("Lobbys erhalten.");
                    for (var lobbyElement : jsonNode.getAsJsonArray("lobbyList")) {
                        JsonObject lobby = lobbyElement.getAsJsonObject();
                        LOGGER.log("ID: " + lobby.get("lobbyID").getAsString() +
                                ", Name: " + lobby.get("lobbyName").getAsString() +
                                ", Spieler: " + lobby.get("currentPlayerCount").getAsInt() + "/" +
                                lobby.get("maxPlayerCount").getAsInt() +
                                ", Passwortgeschützt: " + lobby.get("isPasswordProtected").getAsBoolean());
                    }
                    break;

                case "GAME_STATE":
                    LOGGER.log("Spielstatus aktualisiert! Aktiver Spieler: " + jsonNode.get("activePlayer").getAsString());
                    break;

                case "UNKNOWN_CASE":
                case "ERROR":
                case "INSUFFICIENT_PERMISSIONS":
                    LOGGER.log("Serverfehler: " + jsonNode.get("errorMessage").getAsString());
                    break;

                default:
                    LOGGER.log("Unbekannter Nachrichtentyp: " + type);
                    break;
            }
        } catch (Exception e) {
            LOGGER.log("Fehler beim Verarbeiten der Nachricht: " + e.getMessage());
        }
    }
}