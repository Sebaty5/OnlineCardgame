package de.voidstack_overload.cardgame.records;

import de.voidstack_overload.cardgame.messages.OutgoingMessageType;

public record Message(OutgoingMessageType messageType, String messageBody) {
}
