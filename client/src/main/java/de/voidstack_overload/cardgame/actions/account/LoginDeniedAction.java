package de.voidstack_overload.cardgame.actions.account;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.messages.OutgoingMessageType;

public class LoginDeniedAction extends AccountAction {

    @Override
    public void execute(JsonObject json) {
        super.execute(json);
        super.denied(json, OutgoingMessageType.ACCOUNT_LOGIN);
    }
}
