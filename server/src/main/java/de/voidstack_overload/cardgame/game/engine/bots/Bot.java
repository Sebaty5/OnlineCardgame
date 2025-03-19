package de.voidstack_overload.cardgame.game.engine.bots;

import de.voidstack_overload.cardgame.game.engine.Player;
import de.voidstack_overload.cardgame.network.User;

public class Bot extends Player {
    public Bot(int nameId) {
        super(new User(null, BotNames.botNames[nameId], ""));
        setBot(true);
    }
}
