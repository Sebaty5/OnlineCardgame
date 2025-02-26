package de.voidstack_overload.cardgame.utility;

import de.voidstack_overload.cardgame.messages.MessageTypeServer;
import de.voidstack_overload.cardgame.objects.Response;

public class ResponseBuilder {
    public static Response build(MessageTypeServer type, JsonBuilder builder) {
        builder.add("type", type.name());
        return new Response(type, builder.toString());
    }

    public static Response errorResponse(String error) {
        JsonBuilder builder = new JsonBuilder();
        builder.add("errorMessage", error);
        return build(MessageTypeServer.ERROR, builder);
    }

    public static Response unhandledCaseResponse(String typeName) {
        JsonBuilder builder = new JsonBuilder();
        builder.add("errorMessage", "Unhandled case. Server does not support this request type: " + typeName);
        return build(MessageTypeServer.UNKNOWN_CASE, builder);
    }
}
