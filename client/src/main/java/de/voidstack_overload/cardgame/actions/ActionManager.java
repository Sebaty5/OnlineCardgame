package de.voidstack_overload.cardgame.actions;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.actions.account.LoginAcceptedAction;
import de.voidstack_overload.cardgame.actions.account.LoginDeniedAction;
import de.voidstack_overload.cardgame.actions.account.RegisterAcceptedAction;
import de.voidstack_overload.cardgame.actions.account.RegisterDeniedAction;
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
        actions.put(IncomingMessageType.ACCOUNT_REGISTER_ACCEPT, new RegisterAcceptedAction());
        actions.put(IncomingMessageType.ACCOUNT_LOGIN_DENY, new LoginDeniedAction());
        actions.put(IncomingMessageType.ACCOUNT_REGISTER_DENY, new RegisterDeniedAction());
        // LOBBY


    }

    public static void handleAction(IncomingMessageType type, JsonObject json) {
        if(!actions.containsKey(type)) {
            LOGGER.error("Received unknown action type from server: " + type);
        } else {
            actions.get(type).execute(json);
        }

    }
}
