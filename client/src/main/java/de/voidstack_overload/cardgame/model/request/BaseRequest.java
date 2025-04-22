package de.voidstack_overload.cardgame.model.request;

import de.voidstack_overload.cardgame.connection.MessageType;

public abstract class BaseRequest {
    private final MessageType type;

    public BaseRequest(MessageType type) {
        this.type = type;
    }

    public MessageType getType() {
        return type;
    }
}
