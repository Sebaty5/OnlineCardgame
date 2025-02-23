package de.voidstack_overload.cardgame;

import de.voidstack_overload.cardgame.configuration.Config;

public class Main {
    public static void main(String[] args) {
        Config.INSTANCE.init();
        Config.INSTANCE.load();
    }
}
