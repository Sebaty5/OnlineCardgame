package de.voidstack_overload.cardgame.network;

import de.voidstack_overload.cardgame.logging.StandardLogger;
import de.voidstack_overload.cardgame.objects.User;
import org.java_websocket.WebSocket;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The User manager is responsible for the registration of users and their association with a network connection.
 * @author Sebastian Kiel (Sebaty5)
 */
public class UserManager {
    /**
     * The logger for this class.
     */
    private static final StandardLogger LOGGER = new StandardLogger("UserManager");

    /**
     * The instance of the user manager singleton.
     */
    public static final UserManager INSTANCE = new UserManager();

    /**
     * A map containing the users associated with their network connections.
     */
    private final Map<WebSocket, User> users;

    /**
     * A set reflecting the connected users by their usernames for quick look up.
     */
    private final Set<String> connectedUsers;

    /**
     * The constructor of the user manager.
     */
    private UserManager() {
        users = new ConcurrentHashMap<>();
        connectedUsers = new HashSet<>();
    }

    /**
     * Attempts to return a user registered in the user manager.
     *
     * @param clientConnection the connection used as a key to fetch the user.
     * @return the user which is associated with the connection or null if no user with that connection is registered.
     */
    public User getUser(WebSocket clientConnection) {
        return users.get(clientConnection);
    }

    /**
     * Attempts to add a user to the user manager.
     * Will fail if the user is already registered.
     *
     * @param user the user to be added to the user manager.
     * @return whether the operation was successful.
     */
    public boolean addUser(User user) {
        LOGGER.log("Adding user " + user.getUsername() + " with IP " + user.getWebSocket().getRemoteSocketAddress());
        if(connectedUsers.contains(user.getUsername())) {
            return false;
        }
        connectedUsers.add(user.getUsername());
        return users.putIfAbsent(user.getWebSocket(), user) == null;
    }

    /**
     * Attempts to remove a user from the user manager.
     * Will fail if the user is not registered.
     *
     * @param user the user to be removed.
     * @return whether the operation was successful.
     */
    public boolean removeUser(User user) {
        LOGGER.log("removing user " + user.getUsername() + " with IP " + user.getWebSocket().getRemoteSocketAddress());
        connectedUsers.remove(user.getUsername());
        return users.remove(user.getWebSocket()) != null;
    }
}
