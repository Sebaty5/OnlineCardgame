package de.voidstack_overload.cardgame.actions;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.actions.messages.account.LoginAction;
import de.voidstack_overload.cardgame.logging.StandardLogger;
import de.voidstack_overload.cardgame.messages.MessageTypeClient;
import de.voidstack_overload.cardgame.objects.Message;
import de.voidstack_overload.cardgame.objects.Response;
import de.voidstack_overload.cardgame.utility.ResponseBuilder;
import org.java_websocket.WebSocket;

import java.util.HashMap;
import java.util.Map;

public class ActionManager {
    private static final StandardLogger LOGGER = new StandardLogger("ActionManager");

    private static final Map<MessageTypeClient, Action> actions = new HashMap<>();

    static {
        actions.put(MessageTypeClient.ACCOUNT_LOGIN, new LoginAction());
    }

    public static Response handleAction(MessageTypeClient type, JsonObject json, WebSocket connection) {
        if(!actions.containsKey(type)) {
            LOGGER.error("Unknown action type: " + type);
            return ResponseBuilder.unhandledCaseResponse(type.toString());
        }
        return actions.get(type).execute(json, connection);
    }
}
