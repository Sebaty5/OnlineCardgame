package de.voidstack_overload.cardgame.service;

import de.voidstack_overload.cardgame.messages.OutgoingMessageType;
import de.voidstack_overload.cardgame.network.NetworkManager;
import de.voidstack_overload.cardgame.utility.JsonBuilder;
import de.voidstack_overload.cardgame.utility.MessageBuilder;
import de.voidstack_overload.cardgame.records.User;

public class AuthenticationService {
    private User user;

    public static AuthenticationService INSTANCE = new AuthenticationService();

    private AuthenticationService() {
    }

    public void login(String username, String password) {
        JsonBuilder request = new JsonBuilder();
        request.add("username", username);
        request.add("password", password);
        NetworkManager.INSTANCE.sendMessage(MessageBuilder.build(OutgoingMessageType.ACCOUNT_LOGIN ,request));
    }

    public void register(String username, String password) {
        JsonBuilder request = new JsonBuilder();
        request.add("username", username);
        request.add("password", password);
        NetworkManager.INSTANCE.sendMessage(MessageBuilder.build(OutgoingMessageType.ACCOUNT_REGISTER ,request));
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void logout() {
        this.user = null;
        NetworkManager.INSTANCE.disconnect();
    }
}
