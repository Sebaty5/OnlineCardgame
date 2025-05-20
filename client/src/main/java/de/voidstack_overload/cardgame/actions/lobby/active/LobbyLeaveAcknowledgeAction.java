package de.voidstack_overload.cardgame.actions.lobby.active;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.SceneFXML;
import de.voidstack_overload.cardgame.messages.OutgoingMessageType;
import de.voidstack_overload.cardgame.utility.FxUtility;

public class LobbyLeaveAcknowledgeAction extends LobbyActiveAction {
    @Override
    public void execute(JsonObject json) {
        super.execute(json);
        if(super.isNotExpectedResponse(OutgoingMessageType.LOBBY_LEAVE)) {
            resetConnection(OutgoingMessageType.LOBBY_LEAVE);
            return;
        }

        FxUtility.switchScene(SceneFXML.PROFILE);
    }
}
