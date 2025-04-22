package de.voidstack_overload.cardgame.model.response;

public class LobbyResponse {

    private final String lobbyID;

    public LobbyResponse(String lobbyID) {
        this.lobbyID = lobbyID;
    }

    public String getLobbyID() {
        return lobbyID;
    }
}
