package de.voidstack_overload.cardgame.actions.lobby.passive;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.controller.GameBoardScreenController;
import de.voidstack_overload.cardgame.records.Lobby;
import javafx.application.Platform;

public class LobbyDataAction  extends LobbyPassiveAction {
    @Override
    public void execute(JsonObject json) {
        super.execute(json);
        String lobbyID = json.has("lobbyID") ? json.get("lobbyID").getAsString() : "";
        String lobbyName = json.has("lobbyName") ? json.get("lobbyName").getAsString() : "";
        String host = json.has("host") ? json.get("host").getAsString() : "";
        int currentPlayerCount = json.has("currentPlayerCount") ? json.get("currentPlayerCount").getAsInt() : -1;
        int maxPlayerCount = json.has("maxPlayerCount") ? json.get("maxPlayerCount").getAsInt() : -1;
        boolean isPasswordProtected = json.has("isPasswordProtected") && json.get("isPasswordProtected").getAsBoolean();
        Lobby lobby = new Lobby(lobbyID, lobbyName, currentPlayerCount, maxPlayerCount, isPasswordProtected);
        Platform.runLater(() -> {
            GameBoardScreenController.INSTANCE.updateLobby(lobby);
            GameBoardScreenController.INSTANCE.setHost(host);
        });
    }
}
