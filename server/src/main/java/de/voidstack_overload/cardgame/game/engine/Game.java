package de.voidstack_overload.cardgame.game.engine;

import de.voidstack_overload.cardgame.game.lobby.Lobby;

import java.util.ArrayList;

public class Game {
    private final Lobby lobby;
    private final Board board;

    public Game(Lobby lobby) {
        this.lobby = lobby;
        this.board = new Board(new ArrayList<>(lobby.getPlayers()));
    }
}
