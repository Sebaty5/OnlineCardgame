package de.voidstack_overload.cardgame.game.lobby;

import de.voidstack_overload.cardgame.logging.StandardLogger;
import de.voidstack_overload.cardgame.messages.OutgoingMessageType;
import de.voidstack_overload.cardgame.objects.Player;
import de.voidstack_overload.cardgame.objects.Response;
import de.voidstack_overload.cardgame.objects.User;
import de.voidstack_overload.cardgame.utility.JsonBuilder;
import de.voidstack_overload.cardgame.utility.ResponseBuilder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a game lobby
 */
public class Lobby {
    protected static final StandardLogger LOGGER = new StandardLogger("Lobby");

    private final String id;
    public String getId() {
        return this.id;
    }

    private String password;
    public String getPassword() {
        return this.password;
    }

    private String lobbyName;
    public String getLobbyName() {
        return lobbyName;
    }

    private int maxPlayers;
    public int getMaxPlayers() {
        return maxPlayers;
    }

    private int botCount;
    public int getBotCount() {
        return botCount;
    }

    private Player host;
    public Player getHost() {
        return host;
    }

    private final Set<Player> players = Collections.synchronizedSet(new HashSet<>());
    private boolean isFull = false;
    private boolean isInGame = false;

    public Lobby(String id, String lobbyName, String lobbyPassword, int maxPlayers, int botCount, User host) {
        this.id = id;
        this.host = new Player(host);
        updateLobbyName(lobbyName);
        updateLobbyPassword(lobbyPassword);
        updateMaxPlayers(maxPlayers);
        updateBotCount(botCount);
        this.players.add(this.host);
        broadcast(lobbyName + " created by " + host.getUsername());
    }

    public void updateLobbyName(String lobbyName) {
        if(lobbyName.isEmpty()) {
            this.lobbyName = "Unnamed Lobby";
        } else {
            this.lobbyName = lobbyName.length() > 50 ? lobbyName.substring(0, 50) : lobbyName;
        }
    }

    public void updateLobbyPassword(String password) {
        this.password = password;
    }

    public void updateMaxPlayers(Integer maxPlayers) {
        this.maxPlayers = Math.max(2, Math.min(maxPlayers, 8));
    }

    public void updateBotCount(Integer botCount) {
        this.botCount = Math.max(1, Math.min(botCount, 8));
    }

    public Response addPlayer(User user, String password) {
        JsonBuilder jsonBuilder = new JsonBuilder();

        if(isFull) {
            jsonBuilder.add("errorMessage", "The lobby is already full.");
            return ResponseBuilder.build(OutgoingMessageType.LOBBY_JOIN_DENY, jsonBuilder);
        }

        if(isInGame) {
            jsonBuilder.add("errorMessage", "The lobby is already in a game.");
            return ResponseBuilder.build(OutgoingMessageType.LOBBY_JOIN_DENY, jsonBuilder);
        }

        if(!password.equals(this.password)) {
            jsonBuilder.add("errorMessage","Invalid Password, connection declined.");
            return ResponseBuilder.build(OutgoingMessageType.LOBBY_JOIN_DENY, jsonBuilder);
        }

        Player player = new Player(user);

        players.add(player);
        broadcast("Player joined lobby: " + id);
        if(players.size() >= maxPlayers) {
            isFull = true;
        }
        return ResponseBuilder.build(OutgoingMessageType.LOBBY_JOIN_ACCEPT);
    }

    public void removePlayer(User user) {
        players.remove(user);
        isFull = false;
        if (user == host && !players.isEmpty()) {
            host = players.iterator().next(); // Assign new host
            broadcast(host.getUsername() + " is the new host.");
            LOGGER.log(host.getUsername() + " has been assigned host.");
        }
        broadcast("Player left lobby: " + id);
    }

    public boolean isEmpty() {
        return players.isEmpty();
    }

    public void toggleInGame() {
        this.isInGame = !this.isInGame;
    }

    private void broadcast(String message) {
        JsonBuilder json = new JsonBuilder();
        json.add("message", message);
        players.forEach(player -> player.getWebSocket().send(ResponseBuilder.build(OutgoingMessageType.LOBBY_BROADCAST, json).response()));
    }

    @Override
    public String toString() {
        return "Lobby: " + lobbyName + " | " + id;
    }
}
