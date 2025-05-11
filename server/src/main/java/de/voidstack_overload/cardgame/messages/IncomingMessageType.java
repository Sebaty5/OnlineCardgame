package de.voidstack_overload.cardgame.messages;

public enum IncomingMessageType {
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
    LOBBY_START_GAME,
    LOBBY_SEND_CHAT_MESSAGE,
    //GAME
    GAME_PLAY,
    GAME_PASS,
    //Invalid
    INVALID;

    public static IncomingMessageType fromString(String action) {
        if(action == null) {
            return INVALID;
        }
        try {
            return IncomingMessageType.valueOf(action.toUpperCase());
        } catch (IllegalArgumentException e) {
            return INVALID;
        }
    }
}
