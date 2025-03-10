package de.voidstack_overload.cardgame.game.lobby;

import de.voidstack_overload.cardgame.logging.StandardLogger;
import de.voidstack_overload.cardgame.messages.OutgoingMessageType;
import de.voidstack_overload.cardgame.objects.User;
import de.voidstack_overload.cardgame.objects.Response;
import de.voidstack_overload.cardgame.utility.ResponseBuilder;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LobbyManager {
    private static final StandardLogger LOGGER = new StandardLogger("LobbyManager");

    public static final LobbyManager INSTANCE = new LobbyManager();

    private final Map<String, Lobby> lobbies; // Lobby list
    private final Map<User, String> clientLobbyMap; // Tracks which lobby a client is in

    private LobbyManager() {
        this.lobbies  = new ConcurrentHashMap<>();
        this.clientLobbyMap = new ConcurrentHashMap<>();
    }

    /**
     * Creates a new lobby and assigns the creator as the host.
     */
    public Lobby createLobby(User host, String lobbyName, String lobbyPassword, int maxPlayers, int botCount) {
        if(clientLobbyMap.containsKey(host)) {
            return null;
        }
        String lobbyId = UUID.randomUUID().toString().substring(0, 6);
        Lobby lobby = new Lobby(lobbyId, lobbyName, lobbyPassword, maxPlayers, botCount, host);
        lobbies.put(lobbyId, lobby);
        clientLobbyMap.put(host, lobbyId);
        LOGGER.log("Lobby " + lobbyId + " created by " + host.getWebSocket().getRemoteSocketAddress());
        return lobby;
    }

    /**
     * Joins an existing lobby.
     */
    public Response joinLobby(User user, String lobbyId, String lobbyPassword) {
        if (!lobbies.containsKey(lobbyId)) {
            return ResponseBuilder.errorResponse("Lobby " + lobbyId + " not found.");
        }
        Lobby lobby = lobbies.get(lobbyId);

        Response response = lobby.addPlayer(user, lobbyPassword);
        if(response.responseType().equals(OutgoingMessageType.LOBBY_JOIN_ACCEPT)) {
            clientLobbyMap.put(user, lobbyId);
            LOGGER.log("Client " + user.getWebSocket().getRemoteSocketAddress() + " joined lobby " + lobbyId);
        }
        return response;
    }

    /**
     * Leaves the current lobby.
     */
    public void leaveLobby(User player) {
        String lobbyId = clientLobbyMap.remove(player);
        if (lobbyId != null && lobbies.containsKey(lobbyId)) {
            Lobby lobby = lobbies.get(lobbyId);
            lobby.removePlayer(player);
            if (lobby.isEmpty()) {
                lobbies.remove(lobbyId);
                LOGGER.log("Lobby " + lobbyId + " closed");
            }
        }
    }

    /**
     * Sends a list of active lobbies
     */
    public String getLobbies() {
        if(lobbies.isEmpty()) {
            return "No lobbies found";
        }
        StringBuilder lobbyList = new StringBuilder();
        boolean first = true;
        for (Lobby lobby : lobbies.values()) {
            if(first) {
                lobbyList.append(lobby.toString());
                first = false;
            } else {
                lobbyList.append(", ").append(lobby.toString());
            }
        }
        return lobbyList.toString();
    }

    public boolean isPlayerInLobby(User user) {
        return clientLobbyMap.containsKey(user);
    }
}
