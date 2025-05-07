package de.voidstack_overload.cardgame.application;

import de.voidstack_overload.cardgame.configuration.Config;
import de.voidstack_overload.cardgame.configuration.Settings;

public class Main {
    public static void main(String[] args) {
        Config.getInstance().init();
        Settings.getInstance().init();
        GameClient.main(args);
    }
}
