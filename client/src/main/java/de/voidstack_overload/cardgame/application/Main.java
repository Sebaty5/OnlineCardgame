package de.voidstack_overload.cardgame.application;

import de.voidstack_overload.cardgame.configuration.Config;
import de.voidstack_overload.cardgame.manager.LanguageManager;

import java.util.Locale;

public class Main {
    public static void main(String[] args) {

        LanguageManager.setLanguage(Locale.ENGLISH);

        Config.getInstance().init();
        GameClient.main(args);
    }
}
