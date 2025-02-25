package de.voidstack_overload.cardgame.objects;

import de.voidstack_overload.cardgame.messages.MessageTypeServer;

public record Response(MessageTypeServer responseType, String response) {
}
