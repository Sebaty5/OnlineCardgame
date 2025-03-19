package de.voidstack_overload.cardgame.actions;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.actions.messages.account.LoginAction;
import de.voidstack_overload.cardgame.actions.messages.account.LogoutAction;
import de.voidstack_overload.cardgame.actions.messages.account.RegisterAction;
import de.voidstack_overload.cardgame.actions.messages.lobby.*;
import de.voidstack_overload.cardgame.logging.StandardLogger;
import de.voidstack_overload.cardgame.messages.IncomingMessageType;
import de.voidstack_overload.cardgame.network.Response;
import de.voidstack_overload.cardgame.utility.ResponseBuilder;
import org.java_websocket.WebSocket;

import java.util.HashMap;
import java.util.Map;

public class ActionManager {
    private static final StandardLogger LOGGER = new StandardLogger("ActionManager");

    private static final Map<IncomingMessageType, Action> actions = new HashMap<>();

    static {
        //Account actions
        actions.put(IncomingMessageType.ACCOUNT_LOGIN, new LoginAction());
        actions.put(IncomingMessageType.ACCOUNT_REGISTER, new RegisterAction());
        actions.put(IncomingMessageType.ACCOUNT_LOGOUT, new LogoutAction());
        //Lobby actions
        actions.put(IncomingMessageType.LOBBY_CREATE, new LobbyCreateAction());
        actions.put(IncomingMessageType.LOBBY_JOIN, new LobbyJoinAction());
        actions.put(IncomingMessageType.LOBBY_LEAVE, new LobbyLeaveAction());
        actions.put(IncomingMessageType.LOBBY_UPDATE, new LobbyUpdateAction());
        actions.put(IncomingMessageType.LOBBY_LIST, new LobbyListAction());
        //Game actions
    }

    public static Response handleAction(IncomingMessageType type, JsonObject json, WebSocket connection) {
        if(!actions.containsKey(type)) {
            LOGGER.error("Unknown action type: " + type);
            return ResponseBuilder.unhandledCaseResponse(type.toString());
        }
        return actions.get(type).execute(json, connection);
    }
}
