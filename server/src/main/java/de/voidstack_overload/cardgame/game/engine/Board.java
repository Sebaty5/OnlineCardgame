package de.voidstack_overload.cardgame.game.engine;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.game.engine.cards.Card;
import de.voidstack_overload.cardgame.game.engine.cards.CardColor;
import de.voidstack_overload.cardgame.messages.OutgoingMessageType;
import de.voidstack_overload.cardgame.utility.JsonBuilder;
import de.voidstack_overload.cardgame.utility.ResponseBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
    private List<Player> playerList;
    private List<Player> spectatorList;
    private int mainAttackerIndex;
    private Player activePlayer;
    private Player attacker;
    private Player secondAttacker;
    private Player defender = null;
    private List<Card> drawPile;
    private CardColor trumpColor;
    private Card[][] stacks = new Card[6][2];

    public Board(List<Player> players) {
        resetBoard(players);
    }

    public void resetBoard(List<Player> players) {
        emptyStacks();
        this.drawPile = createDrawPile();
        this.trumpColor = drawPile.getLast().cardColor();
        this.playerList = players;
        this.spectatorList = new ArrayList<>();
        Collections.shuffle(playerList);
        selectPlayerArea(0);
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

    public boolean playCard(Card card, Player player) {
        if (!player.equals(activePlayer) || !player.getHand().contains(card)) {
            return false;
        }

        boolean validPlay = false;
        if(activePlayer == attacker) {
            validPlay = attackPlay(card, player);
        } else if (activePlayer == defender) {
            validPlay = defensePlay(card, player);
        }
        return validPlay;
    }
    private boolean attackPlay(Card card, Player player) {
        boolean validPlay = playAttackCard(card);
        if(validPlay) {
            player.getHand().remove(card);
            if(drawPile.isEmpty() && player.getHand().isEmpty()) {
                if(playerList.size() > 2) {
                    attacker = secondAttacker;
                    secondAttacker = null;
                }
                playerList.remove(player);
                spectatorList.add(player);
            }
        }
        return validPlay;
    }
    private boolean playAttackCard(Card card) {
        if(stacks[0][0] == null) {
            stacks[0][0] = card;
            return true;
        }
        boolean validPlay = false;
        for(Card[] stack : stacks) {
            if(stack[0] != null && stack[1] != null && (stack[0].cardValue() == card.cardValue() || stack[1].cardValue() == card.cardValue())) {
                validPlay = true;
            } else if(stack[0] == null) {
                if(validPlay) {
                    stack[0] = card;
                    return true;
                }
                break;
            }
        }
        return false;
    }
    private boolean defensePlay(Card card, Player player) {
        boolean validPlay = playDefenseCard(card);
        if(validPlay) {
            player.getHand().remove(card);
            if(player.getHand().isEmpty()) {
                if(drawPile.isEmpty()) {
                    playerList.remove(player);
                    spectatorList.add(player);
                }
                defenseWon();
            }
        }
        return validPlay;
    }
    private boolean playDefenseCard(Card card) {
        for(Card[] stack : stacks) {
            if(stack[1] == null) {
                if(card.cardColor() == trumpColor) {
                    if(stack[0].cardColor() == trumpColor) {
                        if(stack[0].cardValue() < card.cardValue()) {
                            stack[1] = card;
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        stack[1] = card;
                        return true;
                    }
                } else {
                    if(stack[0].cardColor() == trumpColor) {
                        return false;
                    } else {
                        if(card.cardColor() == stack[0].cardColor() && stack[0].cardValue() < card.cardValue()) {
                            stack[1] = card;
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
        return false;
    }
    private void defenseWon() {
        redraw();
        selectPlayerArea(mainAttackerIndex + 1);
        emptyStacks();
    }

    private boolean skip(Player player) {
        if(player.equals(attacker)) {

        } else if(player.equals(defender)) {
            //TODO add handling of additional card throws
            defenseLost();
        }
        return false;
    }

    private void defenseLost() {
        addStacksToDefenderHand();
        redraw();
        selectPlayerArea(mainAttackerIndex + 2);
        emptyStacks();
    }

    private void addStacksToDefenderHand() {
        for(Card[] stack : stacks) {
            defender.addCard(stack[0]);
            defender.addCard(stack[1]);
        }
    }

    private void redraw() {
        while(attacker != null && attacker.getHand().size() < 6) {
            if(drawCard(attacker)) return;
        }
        while (secondAttacker != null && secondAttacker.getHand().size() < 6) {
            if(drawCard(secondAttacker)) return;
        }
        while (defender != null && defender.getHand().size() < 6) {
            if(drawCard(defender)) return;
        }
    }

    private boolean drawCard(Player player) {
        if(drawPile.isEmpty()) return true;
        player.addCard(drawPile.removeFirst());
        return false;
    }

    private void emptyStacks() {
        stacks = new Card[6][2];
    }

    private void selectPlayerArea(int startingIndex) {
        if(startingIndex < 0) {
            startingIndex = 0;
        } else if(startingIndex >= playerList.size()) {
            startingIndex = startingIndex % playerList.size();
        }
        mainAttackerIndex = startingIndex;
        activePlayer = playerList.get(startingIndex);
        attacker = playerList.get(startingIndex);
        defender = playerList.get(startingIndex + 1 < playerList.size() ? startingIndex + 1 : startingIndex + 1 - playerList.size());
        if(playerList.size() > 2) {
            secondAttacker = playerList.get(startingIndex + 2 < playerList.size() ? startingIndex + 2 : startingIndex + 2 - playerList.size());
        } else {
            secondAttacker = null;
        }
    }

    private boolean togglePrimaryAttacker() {
        if(secondAttacker == null) {
            return false;
        }
        Player temp = secondAttacker;
        secondAttacker = attacker;
        attacker = temp;
        activePlayer = attacker;
        return true;
    }

    public void sendGameState() {
        for(Player player : playerList) {
            if(player.isBot()) continue;
            JsonBuilder gameState = toJson();
            JsonArray playerHandJson = new JsonArray();
            for(Card card : player.getHand()) {
                playerHandJson.add(card.cardToInt());
            }
            gameState.add("hand", playerHandJson);
            player.getWebSocket().send(ResponseBuilder.build(OutgoingMessageType.GAME_STATE, gameState).response());
        }
        for(Player player : spectatorList) {
            player.getWebSocket().send(ResponseBuilder.build(OutgoingMessageType.GAME_STATE, toJson()).response());
        }
    }

    private JsonBuilder toJson() {
        JsonBuilder json = new JsonBuilder();
        json.add("activePlayer", activePlayer.getUsername());
        json.add("attackers", getAttackerJsonArray());
        json.add("defender", defender.getUsername());
        json.add("players", getPlayersJsonArray());
        json.add("drawPileHeight", drawPile.size());
        json.add("trumpColor", this.trumpColor.getColorAsInt());
        json.add("cardStacks", getStacksJsonArray());
        return json;
    }
    private JsonArray getAttackerJsonArray() {
        JsonArray attackersJsonArray = new JsonArray();
        JsonObject attackerJson = new JsonObject();
        attackerJson.addProperty("playerName", this.attacker.getUsername());
        attackersJsonArray.add(attackerJson);
        if(this.secondAttacker != null) {
            JsonObject attackerAlternative = new JsonObject();
            attackerAlternative.addProperty("playerName", secondAttacker.getUsername());
            attackersJsonArray.add(attackerAlternative);
        }
        return attackersJsonArray;
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
                stackJson.add(stack[0].cardValue() * (1 + stack[0].cardColor().getColorAsInt()));
                if(stack[1] != null) {
                    stackJson.add(stack[1].cardValue() * (1 + stack[1].cardColor().getColorAsInt()));
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
