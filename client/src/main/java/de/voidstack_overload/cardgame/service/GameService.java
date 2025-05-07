package de.voidstack_overload.cardgame.service;

import com.google.gson.JsonObject;
import de.voidstack_overload.cardgame.messages.OutgoingMessageType;
import de.voidstack_overload.cardgame.network.NetworkManager;
import de.voidstack_overload.cardgame.records.GameState;
import de.voidstack_overload.cardgame.records.Player;
import de.voidstack_overload.cardgame.utility.JsonBuilder;
import de.voidstack_overload.cardgame.utility.MessageBuilder;

import java.util.ArrayList;

public class GameService {
    public static GameState parseJsonToGameState(JsonObject json) {
        String activePlayer = json.has("activePlayer") ? json.get( "activePlayer").getAsString() : null;

        String[] attackers = json.has("attackers") ? json.get("attackers").getAsJsonArray().toString().split(",") : null;

        String defender = json.has("defender") ? json.get("defender").getAsString() : null;

        ArrayList<Player> attackerPlayers = new ArrayList<>();
        if(json.has("players")) {
            json.get("players").getAsJsonArray().forEach(player -> {
                JsonObject playerJson = player.getAsJsonObject();
                String name = playerJson.has("playerName") ? playerJson.get("playerName").getAsString() : null;
                int handSize = playerJson.has("handSize") ? playerJson.get("handSize").getAsInt() : -1;
                attackerPlayers.add(new Player(name, handSize));
            });
        }
        Player[] players = attackerPlayers.toArray(Player[]::new);

        int drawPileHeight = json.has("drawPileHeight") ? json.get("drawPileHeight").getAsInt() : -1;

        int trumpColor = json.has("trumpColor") ? json.get("trumpColor").getAsInt() : -1;

        ArrayList<Integer> handCardArray = new ArrayList<>();
        if(json.has("hand")) {
            json.get("hand").getAsJsonArray().forEach(handCardJson -> {
                int handCard = handCardJson.getAsInt();
                handCardArray.add(handCard);
            });
        }
        int[] hand = handCardArray.stream().mapToInt(Integer::intValue).toArray();

        ArrayList<int[]> cardStackArray = new ArrayList<>();
        if(json.has("cardStacks")) {
            json.get("cardStacks").getAsJsonArray().forEach(stack -> {
                ArrayList<Integer> cardArray = new ArrayList<>();
                        stack.getAsJsonArray().forEach(card -> {
                    cardArray.add(card.getAsInt());
                });
                int[] cards = cardArray.stream().mapToInt(Integer::intValue).toArray();
                cardStackArray.add(cards);
            });
        }
        int[][] cardStacks = cardStackArray.toArray(int[][]::new);

        return new GameState(activePlayer, attackers, defender, players, drawPileHeight, trumpColor, hand, cardStacks);
    }

    public static void sendPlayedCard(int card) {
        JsonBuilder jsonBuilder = new JsonBuilder();
        jsonBuilder.add("card", card);
        NetworkManager.INSTANCE.sendMessage(MessageBuilder.build(OutgoingMessageType.GAME_PLAY, jsonBuilder));
    }

    public static void pass() {
        NetworkManager.INSTANCE.sendMessage(MessageBuilder.build(OutgoingMessageType.GAME_PASS));
    }


}