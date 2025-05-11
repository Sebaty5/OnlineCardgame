package de.voidstack_overload.cardgame.actions.lobby.passive;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.controller.GameBoardScreenController;
import javafx.application.Platform;

public class LobbyBroadcastAction extends LobbyPassiveAction {
    @Override
    public void execute(JsonObject json) {
        super.execute(json);
        String message = json.has("message") ? json.get("message").getAsString() : "Invalid Broadcast";
        Platform.runLater(() -> {
            GameBoardScreenController cont = GameBoardScreenController.INSTANCE;
            if (cont != null)
            {
                cont.attachToChatHistory(message);
            }
        });
    }
}
