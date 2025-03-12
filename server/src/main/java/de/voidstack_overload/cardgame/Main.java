package de.voidstack_overload.cardgame;

import de.voidstack_overload.cardgame.configuration.Config;
import de.voidstack_overload.cardgame.network.NetworkManager;

public class Main {
    public static void main(String[] args) {
        Config.INSTANCE.init();
        NetworkManager.INSTANCE.init();
    }
}
