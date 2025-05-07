package de.voidstack_overload.cardgame.network;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.actions.ActionManager;
import de.voidstack_overload.cardgame.logging.StandardLogger;
import de.voidstack_overload.cardgame.messages.IncomingMessageType;
import de.voidstack_overload.cardgame.utility.JsonBuilder;

public class MessageHandler {
    private static final StandardLogger LOGGER = new StandardLogger("MessageHandler");

    public static final MessageHandler INSTANCE = new MessageHandler();

    private MessageHandler() {

    }

    public void handleMessage(String messageString) {
        JsonObject json = JsonBuilder.parseJsonString(messageString);
        boolean validated = validateMessage(json);
        if (!validated) {
            return;
        }
        String type = json != null ? json.get("type").getAsString() : null;
        IncomingMessageType messageType = IncomingMessageType.fromString(type);
        ActionManager.handleAction(messageType, json);
    }

    private static boolean validateMessage(JsonObject json) {
        if(json == null) {
            LOGGER.error("Received invalid JSON-formated message from server.");
            return false;
        } else if(!json.has("type")) {
            LOGGER.error("Received JSON-message without type attribute from server.");
            return false;
        }
        return true;
    }


}
