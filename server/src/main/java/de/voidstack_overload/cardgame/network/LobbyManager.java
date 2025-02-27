package de.voidstack_overload.cardgame.network;

import de.voidstack_overload.cardgame.logging.StandardLogger;
import de.voidstack_overload.cardgame.objects.Lobby;
import de.voidstack_overload.cardgame.objects.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LobbyManager {
    private static final StandardLogger LOGGER = new StandardLogger("LobbyManager");

    public static final LobbyManager INSTANCE = new LobbyManager();

    private final Map<String, Lobby> lobbies; // Lobby list
    private final Map<Player, String> clientLobbyMap; // Tracks which lobby a client is in

    private LobbyManager() {
        this.lobbies  = new ConcurrentHashMap<>();
        this.clientLobbyMap = new ConcurrentHashMap<>();
    }

    /**
     * Creates a new lobby and assigns the creator as the host.
     */
    public Lobby createLobby(Player host, String lobbyName, int maxPlayers, int botCount) {
        String lobbyId = UUID.randomUUID().toString().substring(0, 6);
        Lobby lobby = new Lobby(lobbyId, lobbyName, maxPlayers, botCount, host);
        lobbies.put(lobbyId, lobby);
        clientLobbyMap.put(host, lobbyId);
        LOGGER.log("Lobby " + lobbyId + " created by " + host.socket().getRemoteSocketAddress());
        return lobby;
    }

    /**
     * Joins an existing lobby.
     */
    public boolean joinLobby(Player player, String lobbyId) {
        if (!lobbies.containsKey(lobbyId)) {
            return false;
        }
        Lobby lobby = lobbies.get(lobbyId);
        if(lobby.addPlayer(player)) {
            clientLobbyMap.put(player, lobbyId);
            LOGGER.log("Client " + player.socket().getRemoteSocketAddress() + " joined lobby " + lobbyId);
            return true;
        }
        // TODO: Add reply to indicate that the lobby is full or in an ongoing game.
        return false;
    }

    /**
     * Leaves the current lobby.
     */
    public void leaveLobby(Player player) {
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

    public boolean isPlayerInLobby(Player player) {
        return clientLobbyMap.containsKey(player);
    }
}
