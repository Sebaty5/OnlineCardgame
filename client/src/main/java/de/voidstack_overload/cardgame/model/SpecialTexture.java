package de.voidstack_overload.cardgame.model;

public enum SpecialTexture {

    ClubsSymbol(0),
    ClubsBlankCard(1),
    DiamondsSymbol(2),
    DiamondsBlankCard(3),
    HeartsSymbol(4),
    HeartsBlankCard(5),
    SpadesSymbol(6),
    SpadesBlankCard(7),
    BackHighsAt(8),
    BackLowsAt(9),
    BlancCard(10),
    SwordIcon(63),
    ShieldIcon(64),
    NullIcon(65);

    private final int value;
    SpecialTexture(int value) {
        this.value = value;
    }

    public int getValue() {return value;}
}
