package de.voidstack_overload.cardgame.records;

public record Lobby(String lobbyID, String lobbyName, int currentPlayerCount, int maxPlayerCount, boolean isPasswordProtected)
{
}
