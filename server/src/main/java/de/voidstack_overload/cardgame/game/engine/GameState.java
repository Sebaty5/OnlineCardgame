package de.voidstack_overload.cardgame.game.engine;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.game.engine.cards.Card;
import de.voidstack_overload.cardgame.game.engine.cards.CardColor;
import de.voidstack_overload.cardgame.utility.JsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameState {
    private final List<Player> playerList;
    private int activePlayerIndex;
    private int attackerIndex;
    private int secondAttackerIndex;
    private int defenderIndex = -1;
    private List<Card> drawPile;
    private CardColor trumpColor;
    private Card[][] stacks = new Card[6][2];

    public GameState(List<Player> players) {
        this.playerList = players;
        Collections.shuffle(playerList);
        activePlayerIndex = 0;
        attackerIndex = 0;
        defenderIndex = 1;
        if(players.size() > 2) {
            secondAttackerIndex = 2;
        }
        this.drawPile = createDrawPile();
        this.trumpColor = drawPile.getLast().color();
        emptyStacks();
    }

    private List<Card> createDrawPile() {
        List<Card> drawPile = new ArrayList<>();
        for(int i = 1; i <= 4; i++) {
            for(int j = 0; j < 13; j++) {
                drawPile.add(new Card(CardColor.getColorFromInt(i), j));
            }
        }
        Collections.shuffle(drawPile);
        return drawPile;
    }

    public Card drawCard() {
        if(drawPile.isEmpty()) {return null;}
        return drawPile.removeFirst();
    }

    public boolean playCard(Card card) {
        if(drawPile.contains(card)) {
            return false;
        }

        if(activePlayerIndex == attackerIndex || activePlayerIndex == secondAttackerIndex) {
            return playAttackCard(card);
        } else if (activePlayerIndex == defenderIndex) {
            return playDefenseCard(card);
        }
        return false;
    }

    private boolean playAttackCard(Card card) {
        for(Card[] stack : stacks) {
            if(stack[0] == null) {
                stack[0] = card;
                return true;
            }
        }
        return false;
    }

    private boolean playDefenseCard(Card card) {
        for(Card[] stack : stacks) {
            if(stack[1] == null) {
                stack[1] = card;
                return true;
            }
        }
        return false;
    }

    private void emptyStacks() {
        for(Card[] row : stacks) {
            for(Card col : row) {
                col = null;
            }
        }
    }

    public JsonBuilder toJson() {
        JsonBuilder json = new JsonBuilder();
        json.add("activePlayer", playerList.get(activePlayerIndex).getUsername());
        json.add("attackers", getAttackerJsonArray());
        json.add("defender", playerList.get(defenderIndex).getUsername());
        json.add("players", getPlayersJsonArray());
        json.add("trumpColor", this.trumpColor.getColorAsInt());
        json.add("cardStacks", getStacksJsonArray());
        return json;
    }

    private JsonArray getAttackerJsonArray() {
        JsonArray attackers = new JsonArray();
        JsonObject attacker = new JsonObject();
        attacker.addProperty("playerName", playerList.get(attackerIndex).getUsername());
        attackers.add(attacker);
        if(this.secondAttackerIndex >= 0 && this.secondAttackerIndex < playerList.size()) {
            JsonObject attackerAlternative = new JsonObject();
            attackerAlternative.addProperty("playerName", playerList.get(secondAttackerIndex).getUsername());
            attackers.add(attackerAlternative);
        }
        return attackers;
    }

    private JsonArray getPlayersJsonArray() {
        JsonArray players = new JsonArray();
        for(Player p : playerList) {
            JsonObject player = new JsonObject();
            player.addProperty("playerName", p.getUsername());
            player.addProperty("handSize", p.getHand().size());
            players.add(player);
        }
        return players;
    }

    private JsonArray getStacksJsonArray() {
        JsonArray stacksJson = new JsonArray();
        for(Card[] stack : this.stacks) {
            JsonArray stackJson = new JsonArray();
            if(stack[0] != null) {
                stackJson.add(stack[0].cardValue() * (1 + stack[0].color().getColorAsInt()));
                if(stack[1] != null) {
                    stackJson.add(stack[1].cardValue() * (1 + stack[1].color().getColorAsInt()));
                } else {
                    stackJson.add(-1);
                }
            } else {
                stackJson.add(-1);
                stackJson.add(-1);
            }
            stacksJson.add(stackJson);
        }
        return stacksJson;
    }
}
