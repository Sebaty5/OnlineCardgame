package de.voidstack_overload.cardgame.model.request;

import de.voidstack_overload.cardgame.connection.MessageType;

public class LobbyCreateRequest extends BaseRequest {

    private final String lobbyName;
    private final String password;
    private final int maxPlayers;
    private final int botAmount;

    public LobbyCreateRequest(String lobbyName, String password, int maxPlayers, int botAmount) {
        super(MessageType.LOBBY_CREATE);
        this.lobbyName = lobbyName;
        this.password = password;
        this.maxPlayers = maxPlayers;
        this.botAmount = botAmount;
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public String getPassword() {
        return password;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getBotAmount() {
        return botAmount;
    }
}
