package de.voidstack_overload.cardgame.game.engine;

import de.voidstack_overload.cardgame.game.lobby.Lobby;

import java.util.ArrayList;

public class Game {
    private final Lobby lobby;
    private final GameState gameState;

    public Game(Lobby lobby) {
        this.lobby = lobby;
        this.gameState = new GameState(new ArrayList<>(lobby.getPlayers()));
    }
}
