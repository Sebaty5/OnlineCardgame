package de.voidstack_overload.cardgame.actions.game.active;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.SceneFXML;
import de.voidstack_overload.cardgame.actions.game.GameAction;
import de.voidstack_overload.cardgame.messages.OutgoingMessageType;
import de.voidstack_overload.cardgame.service.AuthenticationService;
import de.voidstack_overload.cardgame.utility.FxUtility;

public abstract class GameActiveAction extends GameAction {
    @Override
    public void execute(JsonObject json) {
        super.execute(json);
    }

    protected boolean isNotExpectedResponse(OutgoingMessageType type) {
        if(this.lastSendMessage.messageType() != type) {
            LOGGER.error("Server acknowledged " + type + " without client having requested " + type +".");
            return true;
        }
        return false;
    }

    protected void resetConnection(boolean accepted, OutgoingMessageType type) {
        AuthenticationService.INSTANCE.logout();
        FxUtility.switchScene(SceneFXML.MENU);
        String keyword = accepted ? "accepted" : "rejected";
        FxUtility.showAlert("Server " + keyword + type + " without request.\nDisconnecting to reset connection.");
    }
}
