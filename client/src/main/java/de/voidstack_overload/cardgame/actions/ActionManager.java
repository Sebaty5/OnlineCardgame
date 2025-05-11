package de.voidstack_overload.cardgame.actions;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.actions.account.LoginAcceptedAction;
import de.voidstack_overload.cardgame.actions.account.LoginDenyAction;
import de.voidstack_overload.cardgame.actions.account.RegisterAcceptedAction;
import de.voidstack_overload.cardgame.actions.account.RegisterDenyAction;
import de.voidstack_overload.cardgame.actions.game.passive.GameStateAction;
import de.voidstack_overload.cardgame.actions.invalidHandeling.InsufficientPermissionAction;
import de.voidstack_overload.cardgame.actions.invalidHandeling.InvalidAction;
import de.voidstack_overload.cardgame.actions.lobby.active.*;
import de.voidstack_overload.cardgame.actions.lobby.passive.LobbyBroadcastAction;
import de.voidstack_overload.cardgame.actions.lobby.passive.LobbyDataAction;
import de.voidstack_overload.cardgame.logging.StandardLogger;
import de.voidstack_overload.cardgame.messages.IncomingMessageType;

import java.util.HashMap;
import java.util.Map;

public class ActionManager {
    private static final StandardLogger LOGGER = new StandardLogger("ActionManager");

    private static final Map<IncomingMessageType, Action> actions = new HashMap<>();

    static {
        // ACCOUNT
        actions.put(IncomingMessageType.ACCOUNT_LOGIN_ACCEPT, new LoginAcceptedAction());
        actions.put(IncomingMessageType.ACCOUNT_LOGIN_DENY, new LoginDenyAction());
        actions.put(IncomingMessageType.ACCOUNT_REGISTER_ACCEPT, new RegisterAcceptedAction());
        actions.put(IncomingMessageType.ACCOUNT_REGISTER_DENY, new RegisterDenyAction());
        // LOBBY
        actions.put(IncomingMessageType.LOBBY_CREATE_ACCEPT, new LobbyCreateAcceptAction());
        actions.put(IncomingMessageType.LOBBY_CREATE_DENY, new LobbyCreateDenyAction());
        actions.put(IncomingMessageType.LOBBY_JOIN_ACCEPT, new LobbyJoinAcceptAction());
        actions.put(IncomingMessageType.LOBBY_JOIN_DENY, new LobbyJoinDenyAction());
        actions.put(IncomingMessageType.LOBBY_LEAVE_ACKNOWLEDGE, new LobbyLeaveAcknowledgeAction());
        actions.put(IncomingMessageType.LOBBY_LIST_REPLY, new LobbyListReplyAction());
        // TODO LOBBY_UPDATE ONCE RELEVANT
        // LOBBY BROADCAST
        actions.put(IncomingMessageType.LOBBY_BROADCAST, new LobbyBroadcastAction());
        actions.put(IncomingMessageType.LOBBY_DATA, new LobbyDataAction());

        // GAME
        actions.put(IncomingMessageType.GAME_STATE, new GameStateAction());

        // INVALID
        actions.put(IncomingMessageType.INVALID, new InvalidAction());
        actions.put(IncomingMessageType.ERROR, new InvalidAction());
        actions.put(IncomingMessageType.INSUFFICIENT_PERMISSIONS, new InsufficientPermissionAction());
    }

    public static void handleAction(IncomingMessageType type, JsonObject json) {
        if(!actions.containsKey(type)) {
            LOGGER.error("Received unknown action type from server: " + type);
        } else {
            actions.get(type).execute(json);
        }

    }
}
