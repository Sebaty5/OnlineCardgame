package de.voidstack_overload.cardgame.application;


import de.voidstack_overload.cardgame.configuration.Config;

public class Main {
    public static void main(String[] args) {
        Config.getInstance().init();
        GameClient.main(args);
    }
}
