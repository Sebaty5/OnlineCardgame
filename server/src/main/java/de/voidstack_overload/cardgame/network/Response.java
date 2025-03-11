package de.voidstack_overload.cardgame.network;

import de.voidstack_overload.cardgame.messages.OutgoingMessageType;

public record Response(OutgoingMessageType responseType, String response) {
}
