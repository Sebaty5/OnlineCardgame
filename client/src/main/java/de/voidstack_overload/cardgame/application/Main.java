package de.voidstack_overload.cardgame.application;

import de.voidstack_overload.cardgame.configuration.Config;
import de.voidstack_overload.cardgame.configuration.Settings;

public class Main {
    public static void main(String[] args) {
        Config.INSTANCE.init();
        Settings.INSTANCE.init();
        GameClient.main(args);
    }
}
