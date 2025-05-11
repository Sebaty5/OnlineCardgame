package de.voidstack_overload.cardgame.messages;

public enum OutgoingMessageType {
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
    LOBBY_GAME_START,
    LOBBY_SEND_CHAT_MESSAGE,
    //GAME
    GAME_PLAY,
    GAME_PASS,
    //Invalid
    INVALID
}
