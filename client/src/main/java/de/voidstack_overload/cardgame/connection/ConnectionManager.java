package de.voidstack_overload.cardgame.connection;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.voidstack_overload.cardgame.controller.LoginController;
import de.voidstack_overload.cardgame.controller.RegisterController;
import de.voidstack_overload.cardgame.logging.StandardLogger;
import de.voidstack_overload.cardgame.utility.JsonBuilder;
import javafx.application.Platform;
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

    private LoginController loginController;
    private RegisterController registrationController;

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
                    LOGGER.log(message);
                    handleServerMessage(message);
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

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
    public void setRegistrationController(RegisterController registrationController) { this.registrationController = registrationController;}

    private void handleServerMessage(String message) {
        try {
            JsonObject jsonNode = JsonParser.parseString(message).getAsJsonObject();
            String type = jsonNode.get("type").getAsString();
            String errorMessage;
            switch (type) {
                case "ACCOUNT_LOGIN_ACCEPT":
                    LOGGER.log("Login erfolgreich! Benutzername: " + jsonNode.get("username").getAsString());
                    Platform.runLater(() -> {
                        if (loginController != null) {
                            loginController.acceptLogin();
                        }
                    });
                    break;

                case "ACCOUNT_LOGIN_DENY":
                    errorMessage = jsonNode.get("errorMessage").getAsString();
                    LOGGER.log("Login fehlgeschlagen: " + errorMessage);
                    Platform.runLater(() -> {
                        if (loginController != null) {
                            loginController.showError(errorMessage);
                        }
                    });
                    break;

                case "ACCOUNT_REGISTER_ACCEPT":
                    LOGGER.log("Registrierung erfolgreich! Benutzername: " + jsonNode.get("username").getAsString());
                    break;

                case "ACCOUNT_REGISTER_DENY":
                    errorMessage = jsonNode.get("errorMessage").getAsString();
                    LOGGER.log("Registrierung fehlgeschlagen: " + errorMessage);
                    Platform.runLater(() -> {
                        if (registrationController != null) {
                            registrationController.showError(errorMessage);
                        }
                    });
                    break;

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

    public void login(String username, String password) {
        JsonBuilder jsonBuilder = new JsonBuilder();
        jsonBuilder.add("type", "ACCOUNT_LOGIN");
        jsonBuilder.add("username", username);
        jsonBuilder.add("password", password);
        sendMessage(jsonBuilder.toString());
    }

    public void logout() {
        JsonBuilder jsonBuilder = new JsonBuilder();
        jsonBuilder.add("type", "ACCOUNT_LOGOUT");
        sendMessage(jsonBuilder.toString());
    }

    public void register(String username, String password) {
        JsonBuilder jsonBuilder = new JsonBuilder();
        jsonBuilder.add("type", "ACCOUNT_REGISTER");
        jsonBuilder.add("username", username);
        jsonBuilder.add("password", password);
        sendMessage(jsonBuilder.toString());
    }

    public void lobbyCreate(String lobbyName, String password, int maxPlayers, int botAmount) {
        JsonBuilder jsonBuilder = new JsonBuilder();
        jsonBuilder.add("type", "LOBBY_CREATE");
        jsonBuilder.add("lobbyName", lobbyName);
        jsonBuilder.add("password", password);
        jsonBuilder.add("maxPlayers", maxPlayers);
        jsonBuilder.add("botCount", botAmount);
        sendMessage(jsonBuilder.toString());
    }

    public void lobbyJoin(String lobbyID, String password) {
        JsonBuilder jsonBuilder = new JsonBuilder();
        jsonBuilder.add("type", "LOBBY_JOIN");
        jsonBuilder.add("lobbyID", lobbyID);
        jsonBuilder.add("lobbyPassword", password);
        sendMessage(jsonBuilder.toString());
    }

    public void lobbyLeave() {
        JsonBuilder jsonBuilder = new JsonBuilder();
        jsonBuilder.add("type", "LOBBY_LEAVE");
        sendMessage(jsonBuilder.toString());
    }

    public void lobbyUpdate(String lobbyName, String lobbyPassword, int maxPlayers, int botAmount) {
        JsonBuilder jsonBuilder = new JsonBuilder();
        jsonBuilder.add("type", "LOBBY_UPDATE");
        jsonBuilder.add("lobbyName", lobbyName);
        jsonBuilder.add("lobbyPassword", lobbyPassword);
        jsonBuilder.add("maxPlayers", maxPlayers);
        jsonBuilder.add("botCount", botAmount);
        sendMessage(jsonBuilder.toString());
    }

    public void lobbyList() {
        JsonBuilder jsonBuilder = new JsonBuilder();
        jsonBuilder.add("type", "LOBBY_LIST");
        sendMessage(jsonBuilder.toString());
    }
}
