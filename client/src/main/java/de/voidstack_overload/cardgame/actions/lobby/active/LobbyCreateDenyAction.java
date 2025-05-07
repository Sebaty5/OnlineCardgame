package de.voidstack_overload.cardgame.actions.lobby.active;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.messages.OutgoingMessageType;
import de.voidstack_overload.cardgame.utility.FxUtility;

public class LobbyCreateDenyAction extends LobbyActiveAction {
    @Override
    public void execute(JsonObject json) {
        super.execute(json);
        if(super.isNotExpectedResponse(OutgoingMessageType.LOBBY_CREATE)) {
            resetConnection(false, OutgoingMessageType.LOBBY_CREATE);
            return;
        }
        FxUtility.showErrorAlert("Lobby creation denied.");
    }
}
