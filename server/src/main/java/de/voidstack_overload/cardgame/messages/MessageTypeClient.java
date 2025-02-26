package de.voidstack_overload.cardgame.messages;

public enum MessageTypeClient {
    //Account
    ACCOUNT_LOGIN,
    ACCOUNT_LOGOUT,
    ACCOUNT_REGISTER,
    //Lobby
    LOBBY_CREATE,
    LOBBY_JOIN,
    LOBBY_LEAVE,
    LOBBY_UPDATE,
    LOBBY_LIST,
    //GAME
    GAME_PLAY,
    GAME_PASS,
    //Invalid
    INVALID;

    public static MessageTypeClient fromString(String action) {
        if(action == null) {
            return INVALID;
        }
        try {
            return MessageTypeClient.valueOf(action.toUpperCase());
        } catch (IllegalArgumentException e) {
            return INVALID;
        }
    }
}
