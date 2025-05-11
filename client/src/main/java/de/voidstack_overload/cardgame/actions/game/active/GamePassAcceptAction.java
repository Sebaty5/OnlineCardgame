package de.voidstack_overload.cardgame.actions.game.active;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.messages.OutgoingMessageType;

public class GamePassAcceptAction extends GameActiveAction {
    @Override
    public void execute(JsonObject json) {
        super.execute(json);
        if(isNotExpectedResponse(OutgoingMessageType.GAME_PASS)) {
            resetConnection(true, OutgoingMessageType.GAME_PASS);
        }
    }
}
