package de.voidstack_overload.cardgame.objects;

import de.voidstack_overload.cardgame.logging.StandardLogger;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a game lobby
 */
public class Lobby {
    protected static final StandardLogger LOGGER = new StandardLogger("Lobby");

    private final String id;
    private String lobbyName;

    private int maxPlayers;
    private int botCount;

    private Player host;
    private final Set<Player> players = Collections.synchronizedSet(new HashSet<>());
    private boolean isFull = false;
    private boolean isInGame = false;

    public Lobby(String id, String lobbyName, int maxPlayers, int botCount, Player host) {
        this.id = id;
        this.host = host;
        updateLobbyName(lobbyName);
        updateMaxPlayers(maxPlayers);
        updateBotCount(botCount);
        this.players.add(host);
        broadcast(lobbyName + " created by " + host.username());
    }

    public void updateLobbyName(String lobbyName) {
        if(lobbyName.isEmpty()) {
            this.lobbyName = "Unnamed Lobby";
        } else {
            this.lobbyName = lobbyName.length() > 50 ? lobbyName.substring(0, 50) : lobbyName;
        }
    }

    public void updateMaxPlayers(Integer maxPlayers) {
        this.maxPlayers = Math.max(2, Math.min(maxPlayers, 8));
    }

    public void updateBotCount(Integer botCount) {
        this.botCount = Math.max(1, Math.min(botCount, 8));
    }

    public boolean addPlayer(Player player) {
        if(isFull || isInGame) {
            return false;
        }
        players.add(player);
        broadcast("Player joined lobby: " + id);
        if(players.size() >= maxPlayers) {
            isFull = true;
        }
        return true;
    }

    public void removePlayer(Player player) {
        players.remove(player);
        isFull = false;
        if (player == host && !players.isEmpty()) {
            host = players.iterator().next(); // Assign new host
            broadcast(host.username() + " is the new host.");
            LOGGER.log(host.username() + " has been assigned host.");
        }
        broadcast("Player left lobby: " + id);
    }

    public boolean isEmpty() {
        return players.isEmpty();
    }

    public boolean isFull() {
        return isFull;
    }

    public boolean isInGame() {
        return isInGame;
    }

    public void toggleInGame() {
        this.isInGame = !this.isInGame;
    }

    private void broadcast(String message) {
        players.forEach(player -> player.socket().send(message));
    }

    @Override
    public String toString() {
        return "Lobby: " + lobbyName + " | " + id;
    }
}
