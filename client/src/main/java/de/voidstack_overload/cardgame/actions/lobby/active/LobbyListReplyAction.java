package de.voidstack_overload.cardgame.actions.lobby.active;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.controller.LobbyListScreenController;
import de.voidstack_overload.cardgame.messages.OutgoingMessageType;
import de.voidstack_overload.cardgame.records.Lobby;
import javafx.application.Platform;
import java.util.ArrayList;
import java.util.List;

public class LobbyListReplyAction  extends LobbyActiveAction {

    @Override
    public void execute(JsonObject json) {
        super.execute(json);

        if(super.isNotExpectedResponse(OutgoingMessageType.LOBBY_LIST)) {
            resetConnection(OutgoingMessageType.LOBBY_LIST);
            return;
        }

        List<Lobby> lobbyList = new ArrayList<>();
        if(json.has("lobbyList")) {
            JsonArray arr = json.get("lobbyList").getAsJsonArray();
            for (JsonElement element : arr.asList())
            {
                JsonObject object = element.getAsJsonObject();
                String lobbyID = object.has("lobbyID") ? object.get("lobbyID").getAsString() : "";
                String lobbyName = object.has("lobbyName") ? object.get("lobbyName").getAsString() : "";
                int currentPlayerCount = object.has("currentPlayerCount") ? object.get("currentPlayerCount").getAsInt() : -Integer.MAX_VALUE;
                int maxPlayerCount = object.has("maxPlayerCount") ? object.get("maxPlayerCount").getAsInt() : -Integer.MAX_VALUE;
                boolean isPasswordProtected = object.has("isPasswordProtected") && object.get("isPasswordProtected").getAsBoolean();

                lobbyList.add(new Lobby(lobbyID, lobbyName, currentPlayerCount, maxPlayerCount, isPasswordProtected));
            }
        }
        Platform.runLater(() -> {
            LobbyListScreenController cont = LobbyListScreenController.INSTANCE;
            if (cont != null)
            {
                cont.setList(lobbyList);
            }
        });

    }
}
