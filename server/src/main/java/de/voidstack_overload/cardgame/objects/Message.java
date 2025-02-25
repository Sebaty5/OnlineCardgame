package de.voidstack_overload.cardgame.objects;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.messages.MessageTypeClient;

public class Message {
    private MessageTypeClient messageType;
    private JsonObject messageJson;
    private Response response;

    public Message(MessageTypeClient messageType, JsonObject messageJson, Response response) {
        this.messageType = messageType;
        this.messageJson = messageJson;
        this.response = response;
    }

    public MessageTypeClient getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageTypeClient messageType) {
        this.messageType = messageType;
    }

    public JsonObject getMessageJson() {
        return messageJson;
    }

    public void setResponse(Response response) {
        if (response != null) {
            this.response = response;
        }
    }

    public Response getResponse() {
        return response;
    }
}
