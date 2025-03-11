package de.voidstack_overload.cardgame.utility;

import de.voidstack_overload.cardgame.messages.IncomingMessageType;
import de.voidstack_overload.cardgame.messages.OutgoingMessageType;
import de.voidstack_overload.cardgame.network.Response;

public class ResponseBuilder {
    public static Response build(OutgoingMessageType type, JsonBuilder builder) {
        builder.add("type", type.name());
        return new Response(type, builder.toString());
    }

    public static Response build(OutgoingMessageType type) {
        JsonBuilder builder = new JsonBuilder();
        return build(type, builder);
    }

    public static Response errorResponse(String error) {
        JsonBuilder builder = new JsonBuilder();
        builder.add("errorMessage", error);
        return build(OutgoingMessageType.ERROR, builder);
    }

    public static Response unhandledCaseResponse(String typeName) {
        JsonBuilder builder = new JsonBuilder();
        builder.add("errorMessage", "Unhandled case. Server does not support this request type: " + typeName);
        return build(OutgoingMessageType.UNKNOWN_CASE, builder);
    }

    public static Response insufficientPermissionResponse(IncomingMessageType type) {
        JsonBuilder builder = new JsonBuilder();
        builder.add("errorMessage", "Access denied. Not a valid user login.");
        builder.add("attemptedAction", type.name());
        return build(OutgoingMessageType.INSUFFICIENT_PERMISSIONS, builder);
    }

}
