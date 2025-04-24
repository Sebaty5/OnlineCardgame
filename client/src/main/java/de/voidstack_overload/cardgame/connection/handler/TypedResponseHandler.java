package de.voidstack_overload.cardgame.connection.handler;

import de.voidstack_overload.cardgame.connection.MessageType;
import de.voidstack_overload.cardgame.logging.StandardLogger;

public abstract class TypedResponseHandler implements ServerResponseHandler {

    private final String typePrefix;
    protected final StandardLogger logger;
    protected MessageType messageType;

    protected TypedResponseHandler(String typePrefix) {
        this.typePrefix = typePrefix;
        this.logger = new StandardLogger("Client");
    }

    @Override
    public boolean canHandle(String type) {
        if (type.startsWith(typePrefix)) {
            this.messageType = MessageType.valueOf(type);
            return true;
        } else return false;
    }
}
