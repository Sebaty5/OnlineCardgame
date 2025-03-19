package de.voidstack_overload.cardgame.game.engine.cards;

public enum CardColor
{
    UNDEFINED_COLOR (-1),
    CLUB (0),
    DIAMONDS (1),
    HEARTS (2),
    SPADES (3);

    private final int code;

    CardColor(int code) {
        this.code = code;
    }

    public int getColorAsInt() {
        return code;
    }


    public static CardColor getColorFromInt(int Nr)
    {
        // first real card is two_of_clubs (index 11),
        // so club is index 11 till index 23 and always gives 0
        // ((11 - 11) / 13) == 0 and ((23 - 11) / 13) == 0
        // next color starts at index 24 and gives 1 und so on
        // club == 0
        // diamonds == 1
        // hearts == 2
        // spades == 3
        if (Nr < 11 || Nr > 62) {
            return UNDEFINED_COLOR; // Return safe fallback
        }

        int cardColor = (Nr - 11) / 13;

        for (CardColor color : CardColor.values()) {
            if (color.getColorAsInt() == cardColor) {
                return color;
            }
        }

        return UNDEFINED_COLOR;
    }
}
