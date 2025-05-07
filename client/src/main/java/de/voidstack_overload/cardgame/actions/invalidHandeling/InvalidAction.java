package de.voidstack_overload.cardgame.actions.invalidHandeling;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.actions.BaseAction;
import de.voidstack_overload.cardgame.utility.FxUtility;

public class InvalidAction extends BaseAction {
    @Override
    public void execute(JsonObject json) {
        super.execute(json);
        String error = json.has("errorMessage") ? json.get("errorMessage").getAsString() : "No error message given.";

        String message = "Server Error: " + error + "\n" + "Your last action was " + lastSendMessage.messageType().toString();
        FxUtility.showErrorAlert(message);
    }
}
