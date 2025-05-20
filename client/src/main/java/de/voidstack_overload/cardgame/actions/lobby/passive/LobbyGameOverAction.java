package de.voidstack_overload.cardgame.actions.lobby.passive;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.controller.GameBoardScreenController;
import javafx.application.Platform;

public class LobbyGameOverAction extends LobbyPassiveAction {
    @Override
    public void execute(JsonObject json) {
        super.execute(json);
        Platform.runLater(() -> {
            GameBoardScreenController.INSTANCE.setInGame(false);
            GameBoardScreenController.INSTANCE.updateLobby(GameBoardScreenController.INSTANCE.getLobby());
        });
    }
}
