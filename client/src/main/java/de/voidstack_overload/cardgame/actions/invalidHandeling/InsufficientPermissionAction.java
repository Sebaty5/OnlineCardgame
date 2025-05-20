package de.voidstack_overload.cardgame.actions.invalidHandeling;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.actions.BaseAction;
import de.voidstack_overload.cardgame.utility.FxUtility;

public class InsufficientPermissionAction extends BaseAction {
    @Override
    public void execute(JsonObject json) {
        super.execute(json);
        String error = json.has("errorMessage") ? json.get("errorMessage").getAsString() : "No error message given.";
        String attemptedAction = json.has("attemptedAction") ? json.get("attemptedAction").getAsString() : "No attempted action given.";

        String message = "Server Error: " + error + "\nYour last action was " + lastSendMessage.messageType().toString() + ".\nThe server rejected the following action: " + attemptedAction + ".";
        FxUtility.showErrorAlert(message);
    }
}
