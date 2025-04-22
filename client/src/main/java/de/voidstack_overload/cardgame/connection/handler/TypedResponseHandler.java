package de.voidstack_overload.cardgame.connection.handler;

import de.voidstack_overload.cardgame.connection.MessageType;
import de.voidstack_overload.cardgame.logging.StandardLogger;
import java.util.Set;

public abstract class TypedResponseHandler implements ServerResponseHandler {

    private final Set<MessageType> supportedTypes;
    protected final StandardLogger logger;
    protected MessageType messageType;

    protected TypedResponseHandler(Set<MessageType> supportedTypes) {
        this.supportedTypes = supportedTypes;
        this.logger = new StandardLogger("Client");
    }

    @Override
    public boolean canHandle(String type) {
        MessageType messageType = MessageType.valueOf(type);

        if (supportedTypes.contains(messageType)) {
            this.messageType = messageType;
            return true;
        } else return false;
    }
}
