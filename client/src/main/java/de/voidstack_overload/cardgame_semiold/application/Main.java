package de.voidstack_overload.cardgame_semiold.application;

import de.voidstack_overload.cardgame_semiold.configuration.Config;

public class Main {
    public static void main(String[] args) {
        Config.getInstance().init();
        GameClient.main(args);
    }
}
