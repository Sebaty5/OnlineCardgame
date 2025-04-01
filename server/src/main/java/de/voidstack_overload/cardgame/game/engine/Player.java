package de.voidstack_overload.cardgame.game.engine;

import de.voidstack_overload.cardgame.game.engine.cards.Card;
import de.voidstack_overload.cardgame.network.User;

import java.util.ArrayList;
import java.util.List;

public class Player extends User {
    private final List<Card> handCards = new ArrayList<>();

    private boolean isBot = false;
    public void setBot(boolean bot) {
        isBot = bot;
    }
    public boolean isBot() {
        return isBot;
    }

    public Player(User user) {
        super(user.getWebSocket(), user.getUsername(), user.getPassword());
    }


    public List<Card> getHand() {
        return handCards;
    }

    public void addCard(Card card) {
        handCards.add(card);
    }
}
