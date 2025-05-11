package de.voidstack_overload.cardgame.messages;

public enum OutgoingMessageType {
    //Account
    ACCOUNT_LOGIN_ACCEPT,
    ACCOUNT_LOGIN_DENY,
    ACCOUNT_REGISTER_ACCEPT,
    ACCOUNT_REGISTER_DENY,
    //Lobby Client interaction
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
    //Lobby Broadcasts
    LOBBY_DATA,
    LOBBY_BROADCAST,
    //Game Client interaction
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
}
