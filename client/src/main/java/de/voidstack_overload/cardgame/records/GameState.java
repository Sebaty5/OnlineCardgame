package de.voidstack_overload.cardgame.records;

public record GameState(String activePlayer, String[] attackers, String defender, Player[] players, int drawPileHeight, int trumpColor, int[] hand, int[][] cardStacks) {
}
