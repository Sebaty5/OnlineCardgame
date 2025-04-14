package de.voidstack_overload.cardgame.manager;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageManager {

    private static ResourceBundle bundle;

    public static void setLanguage(Locale locale) {
        bundle = ResourceBundle.getBundle("languages.messages", locale);
    }

    public static String getString(String key) {
        if (bundle == null) {
            setLanguage(Locale.ENGLISH);
        }
        return bundle.getString(key);
    }

    public static ResourceBundle getBundle() {
        return bundle;
    }

}
