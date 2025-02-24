package de.voidstack_overload.cardgame.network;

import de.voidstack_overload.cardgame.logging.StandardLogger;
import de.voidstack_overload.cardgame.objects.Player;
import org.java_websocket.WebSocket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The player manager is responsible for the registration of players and their association with a network connection.
 * @author Sebastian Kiel (Sebayt5)
 */
public class PlayerManager {
    /**
     * The logger for this class.
     */
    private static final StandardLogger LOGGER = new StandardLogger("PlayerManager");

    /**
     * The instance of the player manager singleton.
     */
    public static final PlayerManager INSTANCE = new PlayerManager();

    /**
     * A map containing the players associated with their network connections.
     */
    private final Map<WebSocket, Player> players;

    /**
     * The constructor of the player manager.
     */
    private PlayerManager() {
        players = new ConcurrentHashMap<>();
    }

    /**
     * Attempts to return a player registered in the player manager.
     *
     * @param clientConnection the connection used as a key to fetch the player.
     * @return the player which is associated with the connection or null if no player with that connection is registered.
     */
    public Player getPlayer(WebSocket clientConnection) {
        return players.get(clientConnection);
    }

    /**
     * Attempts to add a player to the player manager.
     * Will fail if the player is already registered.
     *
     * @param player the player to be added to the player manager.
     * @return whether the operation was successful.
     */
    public boolean addPlayer(Player player) {
        LOGGER.log("Adding player " + player.username() + " with IP " + player.socket().getRemoteSocketAddress());
        return players.putIfAbsent(player.socket(), player) == null;
    }

    /**
     * Attempts to remove a player from the player manager.
     * Will fail if the player is not registered.
     *
     * @param player the player to be removed.
     * @return whether the operation was successful.
     */
    public boolean removePlayer(Player player) {
        LOGGER.log("removing player " + player.username() + " with IP " + player.socket().getRemoteSocketAddress());
        return players.remove(player.socket()) != null;
    }
}
