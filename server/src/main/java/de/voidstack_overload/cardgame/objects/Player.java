package de.voidstack_overload.cardgame.objects;

public class Player extends User {
    public Player(User user) {
        super(user.getWebSocket(), user.getUsername(), user.getPassword());
    }


}
