package de.voidstack_overload.cardgame.actions;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.logging.StandardLogger;
import de.voidstack_overload.cardgame.records.Message;
import de.voidstack_overload.cardgame.network.NetworkManager;

public abstract class BaseAction implements Action {
    protected static final StandardLogger LOGGER = new StandardLogger();
    protected Message lastSendMessage;

    @Override
    public void execute(JsonObject json) {
         lastSendMessage = NetworkManager.INSTANCE.getLastSentMessage();
    }
}
