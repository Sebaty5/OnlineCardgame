package de.voidstack_overload.cardgame.utility;

import de.voidstack_overload.cardgame.messages.MessageTypeServer;
import de.voidstack_overload.cardgame.objects.Response;

public class ResponseBuilder {
    public static Response errorResponse(String error) {
        JsonBuilder builder = new JsonBuilder();
        builder.add("type", "ERROR");
        builder.add("messageJson", error);
        return new Response(MessageTypeServer.ERROR, builder.toString());
    }

    public static Response unhandledCaseResponse(String type) {
        JsonBuilder builder = new JsonBuilder();
        builder.add("type", "UNKNOWN_CASE");
        builder.add("messageJson", "Unhandled case. Server does not support this request type: " + type);
        return new Response(MessageTypeServer.UNKNOWN_CASE, builder.toString());
    }
}
