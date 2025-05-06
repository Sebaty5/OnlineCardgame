package de.voidstack_overload.cardgame.game.lobby;

import de.voidstack_overload.cardgame.game.engine.Board;
import de.voidstack_overload.cardgame.game.engine.bots.Bot;
import de.voidstack_overload.cardgame.logging.StandardLogger;
import de.voidstack_overload.cardgame.messages.OutgoingMessageType;
import de.voidstack_overload.cardgame.game.engine.Player;
import de.voidstack_overload.cardgame.network.Response;
import de.voidstack_overload.cardgame.network.User;
import de.voidstack_overload.cardgame.utility.JsonBuilder;
import de.voidstack_overload.cardgame.utility.ResponseBuilder;
import org.java_websocket.WebSocket;

import java.util.*;

/**
 * Represents a game lobby
 */
public class Lobby {
    protected static final StandardLogger LOGGER = new StandardLogger();

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
    public Set<Player> getPlayers() {
        return players;
    }
    public int getPlayerCount() {
        return players.size();
    }

    private boolean isFull = false;
    private boolean isInGame = false;
    public boolean isInGame() {
        return isInGame;
    }
    Board board;
    public Board getBoard() {
        return board;
    }

    public Lobby(String id, String lobbyName, String lobbyPassword, int maxPlayers, int botCount, User host) {
        this.id = id;
        this.host = new Player(host);
        updateLobbyName(lobbyName);
        updateLobbyPassword(lobbyPassword);
        updateMaxPlayers(maxPlayers);
        updateBotCount(botCount);
        for (int i = 0; i < botCount; i++) {
            players.add(new Bot(i));
        }

        this.players.add(this.host);
        broadcast(lobbyName + " created by " + host.getUsername());
    }

    public void updateLobbyName(String lobbyName) {
        if(lobbyName.isEmpty()) {
            this.lobbyName = "Unnamed Lobby";
        } else {
            this.lobbyName = lobbyName.length() > 50 ? lobbyName.substring(0, 50) : lobbyName;
        }
        LOGGER.log("Updated lobby Name. New name: " + this.lobbyName);
    }

    public void updateLobbyPassword(String password) {
        this.password = password;
        LOGGER.log("Updated lobby password. New password: " + this.password);
    }

    public void updateMaxPlayers(Integer maxPlayers) {
        this.maxPlayers = Math.max(2, Math.min(maxPlayers, 8));
        LOGGER.log("Updated max player count. New max player count: " + this.maxPlayers);
    }

    public void updateBotCount(Integer botCount) {
        this.botCount = Math.max(0, Math.min(botCount, maxPlayers-1));

        LOGGER.log("Updated bot count. New bot count: " + this.botCount);
    }

    public Response addPlayer(User user, String password) {
        JsonBuilder jsonBuilder = new JsonBuilder();

        if(isFull) {
            LOGGER.log("Lobby is already full.");
            jsonBuilder.add("errorMessage", "The lobby is already full.");
            return ResponseBuilder.build(OutgoingMessageType.LOBBY_JOIN_DENY, jsonBuilder);
        }

        if(isInGame) {
            LOGGER.log("Lobby is already in game.");
            jsonBuilder.add("errorMessage", "The lobby is already in a game.");
            return ResponseBuilder.build(OutgoingMessageType.LOBBY_JOIN_DENY, jsonBuilder);
        }

        if(!password.equals(this.password)) {
            LOGGER.log("Passwords do not match.");
            jsonBuilder.add("errorMessage","Invalid Password, connection declined.");
            return ResponseBuilder.build(OutgoingMessageType.LOBBY_JOIN_DENY, jsonBuilder);
        }

        Player player = new Player(user);
        players.add(player);
        LOGGER.log("Added new player to lobby.");
        broadcast("Player " + player.getUsername() + " joined lobby: " + id);
        if(players.size() >= maxPlayers) {
            LOGGER.log("Max player count reached.");
            isFull = true;
            board = new Board(new ArrayList<>(this.getPlayers()));
            isInGame = true;
        }
        return ResponseBuilder.build(OutgoingMessageType.LOBBY_JOIN_ACCEPT);
    }

    public void removePlayer(User user) {
        LOGGER.log("Removing player from lobby.");
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
        return players.isEmpty() || players.size() - botCount == 0;
    }

    public void toggleInGame() {
        this.isInGame = !this.isInGame;
    }

    private void broadcast(String message) {
        LOGGER.log("Broadcasting message: " + message);
        JsonBuilder json = new JsonBuilder();
        json.add("message", message);
        players.forEach(player -> {
            WebSocket socket =  player.getWebSocket();
            if(socket != null) {
                socket.send(ResponseBuilder.build(OutgoingMessageType.LOBBY_BROADCAST, json).response());
            }
        });
    }

    @Override
    public String toString() {
        return "Lobby: " + lobbyName + " | " + id;
    }


}
