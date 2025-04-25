package de.voidstack_overload.cardgame.game.engine.cards;

public record Card(CardColor cardColor, int cardValue) {
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Card(CardColor color, int value))) return false;
        return cardColor.equals(color) && cardValue == value;
    }

    public int cardToInt() {
        return 11 + cardColor().getColorAsInt() * 13 + cardValue();
    }

    public static Card intToCard(int value) {
        CardColor color = CardColor.getColorFromInt(value);
        int cardValue = (value - 11) % 13;
        return new Card(color, cardValue);
    }

    @Override
    public String toString() {
        return cardValue + " of " + cardColor.toString();
    }
}
