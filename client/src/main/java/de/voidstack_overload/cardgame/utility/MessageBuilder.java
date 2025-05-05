package de.voidstack_overload.cardgame.utility;

import de.voidstack_overload.cardgame.messages.OutgoingMessageType;
import de.voidstack_overload.cardgame.network.Message;

public class MessageBuilder {
    public static Message build(OutgoingMessageType type, JsonBuilder builder) {
        builder.add("type", type.name());
        return new Message(type, builder.toString());
    }

    public static Message build(OutgoingMessageType type) {
        JsonBuilder builder = new JsonBuilder();
        return build(type, builder);
    }
}
