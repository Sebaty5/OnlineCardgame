package de.voidstack_overload.cardgame.network;

public class Message {

    enum MessageType {
        //From Client to Server
        ACCOUNT_LOGIN,
        ACCOUNT_LOGOUT,
        ACCOUNT_REGISTER,

        LOBBY_CREATE,
        LOBBY_JOIN,
        LOBBY_LEAVE,
        LOBBY_UPDATE,
        LOBBY_LIST,

        GAME_PLAY,
        GAME_PASS,

        //From Server to Client
        ACCOUNT_LOGIN_ACCEPT,
        ACCOUNT_LOGIN_DENY,
        ACCOUNT_REGISTER_ACCEPT,
        ACCOUNT_REGISTER_DENY,

        LOBBY_CREATE_ACCEPT,
        LOBBY_CREATE_DENY,
        LOBBY_JOIN_ACCEPT,
        LOBBY_JOIN_DENY,
        LOBBY_LEAVE_ACKNOWLEDGE,

        LOBBY_UPDATE_ACCEPT,
        LOBBY_UPDATE_DENY,
        LOBBY_LIST_REPLY,

        LOBBY_DATA,
        LOBBY_BROADCAST,

        GAME_PLAY_ACCEPT,
        GAME_PLAY_DENY,
        GAME_PASS_ACCEPT,
        GAME_PASS_DENY,
        GAME_STATE,

        UNKNOWN_CASE,
        ERROR,
        INSUFFICIENT_PERMISSIONS
    }
}
