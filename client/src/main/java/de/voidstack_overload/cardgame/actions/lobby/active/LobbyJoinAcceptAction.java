package de.voidstack_overload.cardgame.actions.lobby.active;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.SceneFXML;
import de.voidstack_overload.cardgame.messages.OutgoingMessageType;
import de.voidstack_overload.cardgame.utility.FxUtility;

public class LobbyJoinAcceptAction extends LobbyActiveAction {
    @Override
    public void execute(JsonObject json) {
        super.execute(json);
        if(super.isNotExpectedResponse(OutgoingMessageType.LOBBY_JOIN) && super.isNotExpectedResponse(OutgoingMessageType.LOBBY_LIST)) {
            resetConnection(true, OutgoingMessageType.LOBBY_JOIN);
            return;
        }
        FxUtility.switchScene(SceneFXML.GAMEBOARD);
    }
}
