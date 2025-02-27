package de.voidstack_overload.cardgame.objects;

import de.voidstack_overload.cardgame.messages.OutgoingMessageType;

public record Response(OutgoingMessageType responseType, String response) {
}
