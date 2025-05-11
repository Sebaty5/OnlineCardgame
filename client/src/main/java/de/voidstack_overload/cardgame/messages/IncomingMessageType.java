package de.voidstack_overload.cardgame.messages;

public enum IncomingMessageType {
    //Account
    ACCOUNT_LOGIN_ACCEPT,
    ACCOUNT_LOGIN_DENY,
    ACCOUNT_REGISTER_ACCEPT,
    ACCOUNT_REGISTER_DENY,
    //Lobby
    LOBBY_CREATE_ACCEPT,
    LOBBY_CREATE_DENY,
    LOBBY_JOIN_ACCEPT,
    LOBBY_JOIN_DENY,
    LOBBY_LEAVE_ACKNOWLEDGE,
    LOBBY_UPDATE_ACCEPT,
    LOBBY_UPDATE_DENY,
    LOBBY_LIST_REPLY,
    LOBBY_START_GAME_ACCEPT,
    LOBBY_START_GAME_DENY,
    LOBBY_REQUEST_DATA_ACKNOWLEDGE,
    //Lobby Broadcasts
    LOBBY_DATA,
    LOBBY_BROADCAST,
    //Game
    GAME_PLAY_ACCEPT,
    GAME_PLAY_DENY,
    GAME_PASS_ACCEPT,
    GAME_PASS_DENY,
    //Game Broadcasts
    GAME_STATE,
    //Invalid
    UNKNOWN_CASE,
    ERROR,
    INSUFFICIENT_PERMISSIONS,
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
