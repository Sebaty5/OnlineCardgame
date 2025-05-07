package de.voidstack_overload.cardgame.actions.lobby.active;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.SceneFXML;
import de.voidstack_overload.cardgame.messages.OutgoingMessageType;
import de.voidstack_overload.cardgame.utility.FxUtility;

public class LobbyCreateAcceptAction  extends LobbyActiveAction {
    @Override
    public void execute(JsonObject json) {
        super.execute(json);
        if(super.isNotExpectedResponse(OutgoingMessageType.LOBBY_CREATE)) {
            resetConnection(true, OutgoingMessageType.LOBBY_CREATE);
            return;
        }

        String lobbyID = json.has("lobbyID") ? json.get("lobbyID").getAsString() : "";
        if (lobbyID.isEmpty())
        {
            FxUtility.showErrorAlert("Server accepted lobby creation but failed to send a Lobby ID.");
            // TODO: Add id fetching from server or other handling for this case (Disconnect?)
            return;
        }
        FxUtility.switchScene(SceneFXML.GAMEBOARD);
    }
}
