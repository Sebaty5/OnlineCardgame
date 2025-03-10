package de.voidstack_overload.cardgame.network;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import de.voidstack_overload.cardgame.actions.ActionManager;
import de.voidstack_overload.cardgame.logging.StandardLogger;
import de.voidstack_overload.cardgame.messages.IncomingMessageType;
import de.voidstack_overload.cardgame.objects.Response;
import de.voidstack_overload.cardgame.utility.ResponseBuilder;
import org.java_websocket.WebSocket;

public class MessageHandler {
    private static final StandardLogger LOGGER = new StandardLogger("NetworkManager");

    public static final MessageHandler INSTANCE = new MessageHandler();

    private MessageHandler() {

    }

    public Response handleMessage(String messageString, WebSocket connection) {
        JsonObject json = parseMessage(messageString);
        Response invalidMessageResponse = validateMessage(json);
        if (invalidMessageResponse != null){
            return invalidMessageResponse;
        }
        String type = json != null ? json.get("type").getAsString() : null;
        IncomingMessageType messageType = IncomingMessageType.fromString(type);
        return ActionManager.handleAction(messageType, json, connection);
    }

    private static Response validateMessage(JsonObject json) {
        if(json == null) {
            LOGGER.log("Invalid message");
            return ResponseBuilder.errorResponse("Received invalid JSON-formated message.");
        } else if(!json.has("type")) {
            LOGGER.log("Invalid message: " + json);
            return ResponseBuilder.errorResponse("Received JSON-message without type attribute.");
        }
        return null;
    }

    private JsonObject parseMessage(String message) {
        try {
            JsonElement jsonElement = JsonParser.parseString(message);
            if (jsonElement.isJsonObject()) {
                return jsonElement.getAsJsonObject();
            }
        } catch (JsonSyntaxException | NullPointerException e) {
            return null;
        }
        return null;
    }
}
